package com.ada.federate;

import com.ada.federate.config.ClientConfig;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.rpc.impl.RPCStub;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.SQLUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * user interface
 */
public class PRCClient {
    public static ExecutorService executorService;
    public static ExecutorService subExecutorService;
    private long startTime, endTime;
    // thread pool
    public static List<String> addressList = new ArrayList<>();
    public static List<RPCStub> RPCStubs = new ArrayList<>();

    public static RPCStub groupingStub, aggregationStub;

    private static int siloSize;

    public PRCClient(ClientConfig config) throws IOException {
        int siloId = 0;
        for (ClientConfig.Endpoint endpoint : config.getEndpoints()) {
            String address = endpoint.getAddress();
            RPCStub RPCStub = new RPCStub(address, siloId++);
            RPCStub.endpoint = endpoint;
            RPCStubs.add(RPCStub);
            addressList.add(address);
        }
        groupingStub = RPCStubs.get(0);
        aggregationStub = RPCStubs.get(1);

        siloSize = addressList.size();
        executorService = Executors.newFixedThreadPool(RPCStubs.size() + 1);
        subExecutorService = Executors.newFixedThreadPool(RPCStubs.size() + 1);
    }

    /**
     * rpc hello
     * - 沟通全集
     */
    public void rpcHello() {
        // startTime = System.currentTimeMillis();
        RPCService.HelloRequest.Builder requestBuilder = RPCService.HelloRequest.newBuilder().setSiloId(0).setIndex(0).setRound(1).addAllEndpoint(addressList);
        RPCStub leader = RPCStubs.get(0);
        leader.rpcHello(requestBuilder.build());
        // endTime = System.currentTimeMillis();
        // timeframeList.put("hello", endTime - startTime);
    }

    public RPCCommon.FinalResult privateQuery(String querySQL) throws Exception {
        long uuid = System.currentTimeMillis();
        RPCCommon.SQLExpression groupExpression = SQLUtils.parseSQL2SQLExpression(querySQL, groupingStub.endpoint, uuid);
        RPCCommon.SQLExpression aggExpression = SQLUtils.parseSQL2SQLExpression(querySQL, aggregationStub.endpoint, uuid);

        groupingStub.groupByADD(groupExpression);

        if (aggExpression.getFuncType() == RPCCommon.FuncType.SUM || aggExpression.getFuncType() == RPCCommon.FuncType.COUNT) {
            RPCCommon.FinalResult rs = aggregationStub.aggregationADD(aggExpression);
            return rs;
        } else {
            RPCCommon.RawIR rawIR = aggregationStub.aggregationORDER(aggExpression);
            RPCCommon.GroupIR groupIR = groupingStub.groupByORDER(groupExpression);
            // LogUtils.debug(rawIR);
            // LogUtils.debug(groupIR);
            RPCCommon.AggregatedIR aggregatedIR = joinAndAggregate(rawIR, groupIR, aggExpression.getFuncType());
            // LogUtils.debug(aggregatedIR);
            RPCCommon.FinalResult finalResult = aggregationStub.decryptAggregatedORDER(aggregatedIR);
            return finalResult;
        }
    }

    public RPCCommon.AggregatedIR joinAndAggregate(RPCCommon.RawIR rawIR, RPCCommon.GroupIR groupIR, RPCCommon.FuncType funcType) {
        RPCCommon.AggregatedIR.Builder builder = RPCCommon.AggregatedIR.newBuilder();
        try {
            Map<String, Long> aggregatedValues = new HashMap<>();

            List<String> keys = groupIR.getKeyList();
            List<RPCCommon.IDs> idGroups = groupIR.getIdList();

            for (int i = 0; i < keys.size(); i++) {
                List<String> ids = idGroups.get(i).getIdList();

                if (funcType == RPCCommon.FuncType.MAX) {
                    Long max = Long.MIN_VALUE;
                    for (String id : ids) {
                        for (int j = 0; j < rawIR.getIdCount(); j++) {
                            if (rawIR.getId(j).equals(id)) {
                                Long value = Long.valueOf(rawIR.getVal(j));
                                max = value > max ? value : max;
                            }
                        }
                    }
                    if (max != Long.MIN_VALUE)
                        aggregatedValues.put(keys.get(i), max);
                } else if (funcType == RPCCommon.FuncType.MEDIAN) {
                    List<Long> values = new ArrayList<>();
                    Collections.sort(values);

                    for (String id : ids) {
                        for (int j = 0; j < rawIR.getIdCount(); j++) {
                            if (rawIR.getId(j).equals(id)) {
                                Long value = Long.valueOf(rawIR.getVal(j));
                                values.add(value);
                            }
                        }
                    }

                    int size = values.size();
                    long median = Long.MIN_VALUE;
                    if (size > 0) {
                        if (size == 1) {
                            median = values.get(0);
                        } else if (size % 2 == 0) {
                            // median = (values.get(size / 2) + values.get((size / 2) - 1)) / 2;
                            median = values.get((size / 2) - 1);
                        } else {
                            median = values.get(size / 2);
                        }
                        aggregatedValues.put(keys.get(i), median);
                    }
                }
            }

            for (String key : aggregatedValues.keySet()) {
                builder.addKey(key);
                builder.addVal(aggregatedValues.get(key).toString());
            }
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
        }
        return builder.build();
    }


    public RPCCommon.FinalResult publicQuery(String querySQL) throws Exception {
        long uuid = System.currentTimeMillis();

        RPCCommon.PublicQueryMessage.Builder queryMessageBuilder = RPCCommon.PublicQueryMessage.newBuilder();
        RPCCommon.SQLExpression groupExpression = SQLUtils.parseSQL2SQLExpression(querySQL, groupingStub.endpoint, uuid);
        RPCCommon.SQLExpression aggregateExpression = SQLUtils.parseSQL2SQLExpression(querySQL, aggregationStub.endpoint, uuid);

        queryMessageBuilder.setGroupExpression(groupExpression)
                .setAggregateExpression(aggregateExpression)
                .setFuncType(aggregateExpression.getFuncType());

        RPCCommon.FinalResult finalResult = aggregationStub.groupByAggregationPlaintext(queryMessageBuilder.build());

        return finalResult;
    }
}
