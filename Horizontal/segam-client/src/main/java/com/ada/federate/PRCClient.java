package com.ada.federate;

import com.ada.federate.cache.*;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.secure.SecureGroupSum;
import com.ada.federate.pojo.ClientConfig;
import com.ada.federate.rpc.impl.RPCStub;
import com.ada.federate.utils.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * user interface
 */
public class PRCClient {
    public static ExecutorService executorService, subExecutorService;

    public static List<String> endpointList = new ArrayList<>();
    public static List<RPCStub> RPCStubs = new ArrayList<>();

    private static int siloSize;

    public PRCClient(ClientConfig config) throws IOException {
        int siloId = 0;
        for (String host : config.getEndpoints()) {
            String ip = host.split(":")[0];
            String port = host.split(":")[1];
            RPCStub client = new RPCStub(host, siloId++);
            RPCStubs.add(client);
            endpointList.add(host);
        }
        siloSize = endpointList.size();
        executorService = Executors.newFixedThreadPool(RPCStubs.size() + 1);
        subExecutorService = Executors.newFixedThreadPool(RPCStubs.size() + 1);
    }

    public void rpcHello(RPCCommon.SQLExpression expression) throws Exception {
        List<String> dimensionNameList = expression.getDimensionsList();

        Map<String, Set<String>> fullKeySetMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(dimensionNameList)) {
            Tools.printRpcHelloInfo();
            List<String> sqlList = new ArrayList<>();
            for (int i = 0; i < dimensionNameList.size(); i++) {
                String dimension = dimensionNameList.get(i);
                if (!expression.getAlias(i).equals("null")) {
                    sqlList.add(String.format("select distinct(%s) as %s from %s.%s;", dimension, expression.getAlias(i), expression.getTargetSchema(), expression.getTableName()));
                } else {
                    sqlList.add(String.format("select distinct(%s) from %s.%s;", dimension, expression.getTargetSchema(), expression.getTableName()));
                }
            }
            RPCCommon.SQLMessage sqlMessage = RPCCommon.SQLMessage.newBuilder().addAllSql(sqlList).setUuid(System.currentTimeMillis()).build();
            List<Callable<RPCCommon.Status>> tasks = new ArrayList<>();
            for (RPCStub client : RPCStubs) {
                tasks.add(() -> client.batchQuery(sqlMessage));
            }
            List<RPCCommon.Status> statusList = ThreadTools.runCallableTasks(executorService, tasks);
            List<ResultColumn> resultColumnList = null;

            for (RPCCommon.Status status : statusList) {
                if (status.getCode()) {
                    resultColumnList = ResultColumn.parse(status.getMessage());
                    for (ResultColumn resultColumn : resultColumnList) {
                        if (fullKeySetMap.containsKey(resultColumn.fieldName)) {
                            fullKeySetMap.get(resultColumn.fieldName).addAll(resultColumn.valList);
                        } else {
                            fullKeySetMap.put(resultColumn.fieldName, new HashSet<>(resultColumn.valList));
                        }
                    }
                } else {
                    throw new Exception(status.getMessage());
                }
            }
        }

        RPCService.HelloRequest.Builder requestBuilder = RPCService.HelloRequest.newBuilder().addAllEndpoint(endpointList);

        for (int i = 0; i < expression.getDimensionsCount(); i++) {
            String key = expression.getAlias(i).equals("null") ? dimensionNameList.get(i) : expression.getAlias(i);
            requestBuilder.addColumn(RPCService.Column.newBuilder().setFieldName(key).addAllFullKeyList(fullKeySetMap.get(key.toLowerCase())));
        }

        List<Runnable> helloTasks = new ArrayList<>();
        int i = 0;
        for (RPCStub client : RPCStubs) {
            int finalI = i;
            String helloMsg = String.format("[RPC Hello]: Hello, data owner [%d]. I'm query requester.", finalI);
            RPCService.HelloRequest request = requestBuilder.setSiloId(finalI).setHelloMessage(helloMsg).build();
            // LogUtils.debug(request);
            helloTasks.add(() -> client.rpcHello(request));
            i++;
        }
        ThreadTools.runRunnableTasks(executorService, helloTasks);

        Tools.printRpcHelloInfo(fullKeySetMap);
    }

    public ResultKVSet privateQuery(RPCCommon.SQLExpression sqlExpression, SQLUtils.AGG_FUNCTION function) throws Exception {
        ResultKVSet resultKVSet = null;
        if (function == null) {
            throw new Exception("function not support.");
        }

        // First stage
        List<Callable<RPCCommon.Status>> queryTasks = new ArrayList<>();
        for (RPCStub client : RPCStubs) {
            queryTasks.add(() -> client.privacyQuery(sqlExpression));
        }
        List<RPCCommon.Status> statusList = ThreadTools.runCallableTasks(executorService, queryTasks);
        ThreadTools.checkStatus(statusList);

        // Second stage
        if (function == SQLUtils.AGG_FUNCTION.SUM || function == SQLUtils.AGG_FUNCTION.COUNT) {
            List<String> unionSet = sqlExpression.getDimensionsCount() != 0 ? privateSetUnion(sqlExpression.getUuid()) : new ArrayList<>();
            // if (LogUtils.DEBUG) LogUtils.debug(unionSet.size());
            resultKVSet = groupBySumSS(sqlExpression.getUuid(), 0, unionSet, 0);
        } else if (function == SQLUtils.AGG_FUNCTION.MAX) {
            resultKVSet = groupByMax(sqlExpression.getUuid(), true);
        } else if (function == SQLUtils.AGG_FUNCTION.AVG) {
            // ResultKVSet resultSum = groupByMax(sqlExpression.getUuid(), true);
            // ResultKVSet resultCount = groupByMax(sqlExpression.getUuid(), true);
        } else if (function == SQLUtils.AGG_FUNCTION.MEDIAN) {
            LogUtils.debug("start median query");
            resultKVSet = groupByMedian(sqlExpression.getUuid());
        }
        return resultKVSet;
    }

    public void cleanBuffer(Long uuid) throws Exception {
        List<Callable<RPCCommon.Status>> tasks = new ArrayList<>();

        for (RPCStub client : RPCStubs) {
            tasks.add(() -> {
                RPCService.CommandResponse response = client.command(RPCService.CommandRequest.newBuilder().setCommandCode(0).setUuid(uuid).build());
                return response.getStatus();
            });
        }

        List<RPCCommon.Status> statusList = ThreadTools.runCallableTasks(executorService, tasks);
        ThreadTools.checkStatus(statusList);
    }

    public long[] sumForMax(Long uuid, int round) throws Exception {

        List<Integer> publicKeys = SecureGroupSum.generatePublicKeyList(siloSize);
        List<Callable<RPCCommon.SSMessage>> aggregateTasks = new ArrayList<>();
        RPCService.GroupBySumSSRequest request = RPCService.GroupBySumSSRequest.newBuilder().setQueryType(1).setUuid(uuid).setRound(round).addAllPublicKey(publicKeys).build();

        for (RPCStub client : RPCStubs) {
            aggregateTasks.add(() -> client.secureGroupSummationBySS(request));
        }

        List<RPCCommon.SSMessage> secretSumList = ThreadTools.runCallableTasks(subExecutorService, aggregateTasks);

        // if (LogUtils.DEBUG)
        // LogUtils.debug(String.format("1.1 get secret sharing sum:\n %s", ResultsUtils.flatList(secretSumList, RPCCommon.SSMessage.class)));
        long[] result = SecureGroupSum.decryptResult(secretSumList, publicKeys, siloSize);
        // LogUtils.debug(String.format("1.2 decrypt result: %s", Arrays.toString(result)));

        return result;
    }


    public ResultKVSet groupBySumSS(Long uuid, Integer queryType, List<String> unionSet, int rounds) throws Exception {

        List<Integer> publicKeys = SecureGroupSum.generatePublicKeyList(siloSize);
        List<Callable<RPCCommon.SSMessage>> aggregateTasks = new ArrayList<>();

        RPCService.GroupBySumSSRequest request = RPCService.GroupBySumSSRequest.newBuilder().addAllKeyList(unionSet).setQueryType(queryType).setUuid(uuid).addAllPublicKey(publicKeys).build();

        for (RPCStub client : RPCStubs) {
            aggregateTasks.add(() -> client.secureGroupSummationBySS(request));
        }

        List<RPCCommon.SSMessage> secretSumList = ThreadTools.runCallableTasks(executorService, aggregateTasks);

        // if (LogUtils.DEBUG) LogUtils.debug(String.format("1.1 get secret sharing sum:\n %s", ResultsUtils.flatList(secretSumList, RPCCommon.SSMessage.class)));

        long[] result = SecureGroupSum.decryptResult(secretSumList, publicKeys, siloSize);

        // if (LogUtils.DEBUG) LogUtils.debug(String.format("1.2 decrypt result: %s", Arrays.toString(result)));

        ResultKVSet resultKVSet = ResultsUtils.array2QueryResult(result, unionSet);
        return resultKVSet;
    }

    public ResultKVSet publicQuery(RPCCommon.SQLExpression sqlExpression) throws Exception {
        List<Callable<RPCCommon.RPCResult>> tasks = new ArrayList<>();
        for (RPCStub client : RPCStubs) {
            tasks.add(() -> client.publicQuery(sqlExpression));
        }
        List<RPCCommon.RPCResult> RPCResults = ThreadTools.runCallableTasks(executorService, tasks);
        return ResultsUtils.assembleResultTable(RPCResults, sqlExpression);
    }


    public ResultKVSet groupByMax(Long uuid, Boolean maxFlag) throws Exception {
        List<String> unionSet = privateSetUnion(uuid);
        long s = 0, e = Integer.MAX_VALUE >> 1;
        // int s = 0, e = 10000;
        ResultKVSet resultKVSet = new ResultKVSet();
        int rounds = 0;
        BinarySign binarySign = new BinarySign(s, e, unionSet);
        List<RPCCommon.Status> statusList = null;
        while (binarySign.judge()) {
            // System.out.println(StringUtils.repeat("=", 30) + "rounds " + rounds + StringUtils.repeat("=", 30));
            for (int i = 0; i < binarySign.size; i++) {
                long threshold = (binarySign.l(i) + binarySign.r(i) + 1) >> 1;
                binarySign.set_threshold(i, threshold);
            }

            RPCService.GroupByMaxRequest groupByMaxRequest = RPCService.GroupByMaxRequest.newBuilder().setUuid(uuid).addAllKeyIndex(binarySign.getUnionSet()).addAllThreshold(binarySign.getThreshold()).build();

            List<Callable<RPCCommon.Status>> tasks = new ArrayList<>();
            for (RPCStub client : RPCStubs) {
                tasks.add(() -> client.secureGroupByMax(groupByMaxRequest));
            }
            statusList = ThreadTools.runCallableTasks(executorService, tasks);

            long[] count = sumForMax(uuid, rounds);

            // LogUtils.debug(String.format("keyList: %s \n threshold: %s \n bound: [%s]  \n result: %s \n", binarySign.getUnionSet(), binarySign.getThreshold(), binarySign.bound2String(), Arrays.toString(count)));

            for (int i = 0; i < binarySign.size; i++) {
                if (count[i] == 0)
                    binarySign.set_r(i, binarySign.threshold(i) - 1);
                    // else if (count.get(i) >= 1)

                else binarySign.set_l(i, binarySign.threshold(i));
                // else if (count == 1)
                // return getThresholdMax(arr, threshold);
            }
            // System.out.println((StringUtils.repeat("=", 30) + "rounds " + rounds + " end" + StringUtils.repeat("=", 30));
            rounds++;
        }

        for (int i = 0; i < binarySign.size; i++) {
            resultKVSet.addRow(binarySign.unionSet(i), binarySign.l(i));
        }
        return resultKVSet;
    }

    public ResultKVSet groupByMedian(Long uuid) throws Exception {

        List<String> unionSet = privateSetUnion(uuid);
        // LogUtils.debug(unionSet.size());
        long s = 0, e = Integer.MAX_VALUE >> 1;
        ResultKVSet resultKVSet = new ResultKVSet();
        BinarySign binarySign = new BinarySign(s, e, unionSet);
        int rounds = 0;
        List<RPCCommon.Status> statusList = null;

        while (binarySign.judge()) {
            // System.out.println(StringUtils.repeat("=", 30) + "rounds " + rounds + StringUtils.repeat("=", 30));
            for (int i = 0; i < binarySign.size; i++) {
                long threshold = binarySign.runEvenList.get(i) ? (binarySign.l(i) + binarySign.r(i)) >> 1 : (binarySign.l(i) + binarySign.r(i) + 1) >> 1;
                binarySign.set_threshold(i, threshold);
            }

            RPCService.GroupByMedianRequest groupByMedianRequest = RPCService.GroupByMedianRequest.newBuilder().setUuid(uuid).addAllEvenFlag(binarySign.runEvenList).addAllKeyIndex(binarySign.getUnionSet()).addAllThreshold(binarySign.getThreshold()).build();

            List<Callable<RPCCommon.Status>> tasks = new ArrayList<>();
            for (RPCStub client : RPCStubs) {
                tasks.add(() -> client.secureGroupByMedian(groupByMedianRequest));
            }
            statusList = ThreadTools.runCallableTasks(executorService, tasks);

            long[] count = sumForMax(uuid, rounds);

            for (int i = 0; i < binarySign.size; i++) {
                if (!binarySign.runEvenList.get(i)) {
                    if (count[i] >= 0) {
                        binarySign.set_r(i, binarySign.threshold(i) - 1);
                        if (count[i] == 0) {
                            binarySign.set_evenFlag(i, true);
                        }
                    } else binarySign.set_l(i, binarySign.threshold(i));
                } else {
                    if (count[i] <= 0) {
                        binarySign.set_l(i, binarySign.threshold(i) + 1);
                    } else binarySign.set_r(i, binarySign.threshold(i));
                }
            }

            // if (LogUtils.DEBUG) LogUtils.debug(String.format("\nkeyList: %s \n threshold: %s \n bound: [%s]  \n result: %s \n", binarySign.getUnionSet(), binarySign.getThreshold(), binarySign.bound2String(), Arrays.toString(count)));

            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < binarySign.size; i++) {
                if (binarySign.l(i) >= binarySign.r(i)) {
                    if (binarySign.evenFlagList.get(i) && !binarySign.runEvenList.get(i)) {
                        binarySign.set_r(i, e);
                        binarySign.set_runEven(i, true);
                        resultKVSet.addRow(binarySign.unionSet(i), binarySign.l(i));
                    } else {
                        resultKVSet.addRow(binarySign.unionSet(i), binarySign.l(i));
                        indices.add(i);
                    }
                }
            }

            binarySign.remove(indices);
            // System.out.println(("\n" + StringUtils.repeat("=", 30) + "rounds " + rounds + " end" + StringUtils.repeat("=", 30)));
            rounds++;
        }

        for (int i = 0; i < binarySign.size; i++) {
            resultKVSet.addRow(binarySign.unionSet(i), binarySign.l(i));
        }
        return resultKVSet;
    }

    public List<String> privateSetUnion(Long uuid) throws Exception {
        int leaderIndex = 0;
        RPCStub leaderClient = RPCStubs.get(leaderIndex);
        RPCService.SetUnionRequest unionRequest = RPCService.SetUnionRequest.newBuilder().setIndex(0).setRound(1).setUuid(uuid).build();

        RPCService.SetUnionResponse response = leaderClient.privateSetUnion(unionRequest);

        if (response.getStatus().getCode()) {
            List<String> unionSet = response.getKeyIndexList();
            // LogUtils.debug(String.format("union set completed : %s", unionSet));
            return unionSet;
        } else {
            throw new Exception(response.getStatus().getMessage());
        }

    }
}
