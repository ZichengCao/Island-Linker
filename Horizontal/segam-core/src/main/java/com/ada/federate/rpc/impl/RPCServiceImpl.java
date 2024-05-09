package com.ada.federate.rpc.impl;

import com.ada.federate.cache.ConcurrentBuffer;
import com.ada.federate.cache.FullKeyListMap;
import com.ada.federate.cache.ResultKVSet;
import com.ada.federate.cache.ResultKVsSet;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.rpc.SegamGrpc;
import com.ada.federate.secure.SecureGroupSum;
import com.ada.federate.secure.SecureUnion;
import com.ada.federate.utils.*;
import io.grpc.stub.StreamObserver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public abstract class RPCServiceImpl extends SegamGrpc.SegamImplBase {
    protected ConcurrentBuffer buffer = new ConcurrentBuffer();

    protected FullKeyListMap fullKeyListMap = new FullKeyListMap();

    protected RPCStub nextClient = null;
    protected List<RPCStub> clientList = new ArrayList<>();
    protected int siloId;
    protected int siloSize;


    // thread pool for secret sharing
    public static ExecutorService executorService;

    @Override
    public void rpcPing(RPCService.PingRequest request, StreamObserver<RPCCommon.Status> responseObserver) {
        LogUtils.debug("[Ping Recv]: " + request.getMsg());
        RPCCommon.Status status = RPCCommon.Status.newBuilder().setMessage(String.format("data owner %d received.", siloId)).setCode(true).build();
        responseObserver.onNext(status);
        responseObserver.onCompleted();
    }


    @Override
    public void rpcHello(RPCService.HelloRequest request, StreamObserver<RPCCommon.Status> responseObserver) {
        RPCCommon.Status status = RPCCommon.Status.newBuilder().setCode(true).build();

        try {
            // LogUtils.debug(request);
            List<String> endpointList = request.getEndpointList();
            siloId = request.getSiloId();
            siloSize = endpointList.size();
            for (int i = 0; i < siloSize; i++) {
                clientList.add(new RPCStub(endpointList.get(i), i));
            }
            nextClient = clientList.get((siloId + 1) % siloSize);

            List<RPCService.Column> columnList = request.getColumnList();

            if (CollectionUtils.isNotEmpty(columnList)) {
                for (RPCService.Column column : columnList) {
                    fullKeyListMap.addKeyList(column.getFieldName(), column.getFullKeyListList());
                }
                // LogUtils.debug(fullKeyPermutationList.size());
            }

            executorService = Executors.newFixedThreadPool(siloSize + 1);

            LogUtils.info(String.format("RPC hello: total [%d] clients, I'm no [%d]", siloSize, siloId));

        } catch (Exception e) {
            status = status.toBuilder().setCode(false).build();
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            responseObserver.onNext(status);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void command(RPCService.CommandRequest request, StreamObserver<RPCService.CommandResponse> responseObserver) {
        RPCService.CommandResponse.Builder responseBuilder = RPCService.CommandResponse.newBuilder();
        RPCCommon.Status status = RPCCommon.Status.newBuilder().setCode(true).build();
        try {
            if (request.getCommandCode() == 0) {
                cleanBuffer(request.getUuid());
                LogUtils.debug("clean buffer finish.");
            }
        } catch (Exception e) {
            status = status.toBuilder().setCode(false).build();
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            responseObserver.onNext(responseBuilder.setStatus(status).build());
            responseObserver.onCompleted();
        }
    }

    private void cleanBuffer(Long uuid) {
        buffer.clean(uuid);
    }

    @Override
    public void privateSetUnion(RPCService.SetUnionRequest request, StreamObserver<RPCService.SetUnionResponse> responseObserver) {
        // RPCCommon.Status status = RPCCommon.Status.newBuilder().setCode(true).build();
        StopWatch.start();
        RPCCommon.Status.Builder statusBuilder = RPCCommon.Status.newBuilder().setCode(true);
        RPCService.SetUnionRequest.Builder requestBuilder = request.toBuilder().clone();
        RPCService.SetUnionResponse.Builder responseBuilder = RPCService.SetUnionResponse.newBuilder();
        try {
            if (request.getRound() == 1) {
                Set<String> localKeyIndexList = buffer.localResultTable == null ? buffer.localResultKeyValuesTable.keySet() : buffer.localResultTable.keySet();
                // if (LogUtils.DEBUG)LogUtils.debug("1.0 full key set:\n " + fullKeyListMap.columnNameList);
                // if (LogUtils.DEBUG)LogUtils.debug("1.1 local key set:\n " + localKeyIndexList);
                buffer.unionCache = new SecureUnion.SecureUnionCache(localKeyIndexList);
                List<String> confusionSet = buffer.unionCache.getConfusionSet(fullKeyListMap);
                // if (LogUtils.DEBUG)LogUtils.debug("1.2 confusion key set:\n " + confusionSet);
                requestBuilder.addAllKeyIndex(confusionSet);
            } else if (request.getRound() == 2) {
                LinkedList<String> keyList = new LinkedList<>(request.getKeyIndexList());
                // if (LogUtils.DEBUG)LogUtils.debug("2.1 global key set:\n " + keyList);
                Iterator<String> iterator = keyList.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (buffer.unionCache.localRandomSetPop(key)) {
                        iterator.remove();
                    }
                }
                // LogUtils.debug("2.2 global key set after removal:\n " + keyList.size());
                if (request.getIndex() == siloSize - 1) {
                    Set<String> uniqueSet = new LinkedHashSet<>(keyList);
                    // if (LogUtils.DEBUG)
                    // LogUtils.debug(String.format("2.3 original list size: [%s], after remove duplicates size: [%s]\n ", keyList.size(), uniqueSet.size()));
                    responseBuilder.addAllKeyIndex(uniqueSet);
                    responseBuilder.setStatus(statusBuilder.build());

                } else {
                    requestBuilder.clearKeyIndex();
                    requestBuilder.addAllKeyIndex(keyList);
                }

            }
            int nextIndex = (request.getIndex() + 1);
            // LogUtils.debug(String.valueOf(nextIndex));
            if (nextIndex == siloSize) requestBuilder.setRound(request.getRound() + 1);
            // System.out.println(siloSize);
            nextIndex = nextIndex % siloSize;
            if (requestBuilder.getRound() <= 2 && nextIndex < siloSize) {
                RPCService.SetUnionRequest unionRequest = requestBuilder.setIndex(nextIndex).build();
                RPCService.SetUnionResponse nextResponse = nextClient.privateSetUnion(unionRequest);
                responseBuilder.setStatus(nextResponse.getStatus()).addAllKeyIndex(nextResponse.getKeyIndexList());
                // LogUtils.debug(nextResponse.getKeyList().toString());
            }
        } catch (Exception e) {
            statusBuilder.setCode(false).setMessage(LogUtils.buildErrorMessage(e));
            responseBuilder.setStatus(statusBuilder.build());
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            StopWatch.addTimeRecord("PSU");
            RPCService.SetUnionResponse build = responseBuilder.build();
            responseObserver.onNext(build);
            responseObserver.onCompleted();
        }
    }

    public List<RPCCommon.SSMessage> encryptedSSMessageList;
    private CountDownLatch latch;
    private final Object lock = new Object();

    @Override
    public void sendSharing(RPCCommon.SSMessage request, StreamObserver<RPCCommon.Status> responseObserver) {
        synchronized (lock) {
            encryptedSSMessageList.add(request);
        }
        latch.countDown();
        if (LogUtils.DEBUG) LogUtils.debug(String.format("[**] receive sharing from silo %d, count down number: %d", request.getFrom(), latch.getCount()));
        responseObserver.onNext(RPCCommon.Status.newBuilder().setCode(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void secureGroupBySumSS(RPCService.GroupBySumSSRequest request, StreamObserver<RPCCommon.SSMessage> responseObserver) {
        RPCCommon.SSMessage.Builder responseBuilder = RPCCommon.SSMessage.newBuilder();
        ResultKVSet resultTable;
        synchronized (lock) {
            encryptedSSMessageList = new ArrayList<>();
        }
        latch = new CountDownLatch(siloSize);
        try {
            int rounds = request.getRound();
            if (LogUtils.DEBUG) LogUtils.debug(StringUtils.repeat("=", 30) + "rounds " + rounds + StringUtils.repeat("=", 30));
            // sum
            if (request.getQueryType() == 0) {
                resultTable = buffer.localResultTable;

                // if (LogUtils.DEBUG) LogUtils.debug(String.format("1.1 record list: [%s]", resultTable.keySet()));

                ResultKVSet fillResultKVSet = new ResultKVSet();

                List<String> fullKeyList = request.getKeyListList();
                // if (LogUtils.DEBUG) LogUtils.debug(fullKeyList.size());
                if (fullKeyList.size() != 0) {
                    for (String key : fullKeyList) {
                        fillResultKVSet.addRow(key, resultTable.getOrDefault(key, 0L));
                    }
                    resultTable = fillResultKVSet;
                }

                // if (LogUtils.DEBUG) LogUtils.debug(String.format("1.2 record list after fill size: [%s]", resultTable.keySet()));

            } else {
                // max
                resultTable = buffer.signList;
                // if (LogUtils.DEBUG)LogUtils.debug(String.format("1.1 sign list: \n [%s]", resultTable));
            }


            long[][] share = SecureGroupSum.localEncrypt(resultTable, request.getPublicKeyList(),
                    siloSize);

            // if (LogUtils.DEBUG) LogUtils.debug(String.format("local encrypt: [%s]", Arrays.deepToString(share)));

            StopWatch.addTimeRecord("local encrypt");

            // if (LogUtils.DEBUG) LogUtils.debug("3. send sharing: ");

            // if (LogUtils.DEBUG) LogUtils.debug("original number " + latch.getCount());

            List<Callable<RPCCommon.Status>> tasks = new ArrayList<>();
            for (int i = 0; i < siloSize; i++) {

                RPCStub client = clientList.get(i);
                RPCCommon.SSMessage ssMessage = RPCCommon.SSMessage.newBuilder().addAllShareVal(Arrays.stream(share[i]).boxed()
                        .collect(Collectors.toList())).setFrom(siloId).build();
                if (i == siloId) {
                    synchronized (lock) {
                        encryptedSSMessageList.add(ssMessage);
                    }
                    latch.countDown();
                    continue;
                }
                int finalI = i;
                tasks.add(() -> client.sendSharing(ssMessage, finalI));
            }

            ThreadTools.runCallableTasks(executorService, tasks);

            // waiting for all shares
            latch.await();

            StopWatch.addTimeRecord("send sharing");

            responseBuilder.addAllShareVal(SecureGroupSum.mergeSSMessage(encryptedSSMessageList));

            if (LogUtils.DEBUG) LogUtils.debug(String.format("4 adding up shares: [%s]", responseBuilder.getShareValList().toString()));
            if (LogUtils.DEBUG) LogUtils.debug(StringUtils.repeat("=", 30) + "rounds " + rounds + " end" + StringUtils.repeat("=", 30));

        } catch (Exception e) {
            responseBuilder.setStatus(RPCCommon.Status.newBuilder().setCode(false).setMessage(LogUtils.buildErrorMessage(e)).build());
            e.printStackTrace();
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            StopWatch.stop();
            // StopWatch.formatPrintTestInfo();
            responseBuilder.setStatus(
                    RPCCommon.Status.newBuilder().setCode(true).build()).build();
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }

    private final Random random = new Random();

    @Override
    public void secureGroupByMedian(RPCService.GroupByMedianRequest request, StreamObserver<RPCCommon.Status> responseObserver) {
        RPCCommon.Status.Builder responseBuilder = RPCCommon.Status.newBuilder();
        try {
            ResultKVsSet resultKVSet = buffer.localResultKeyValuesTable;
            buffer.signList.clean();
            List<Boolean> runEvenList = request.getEvenFlagList();
            for (int i = 0; i < request.getKeyIndexCount(); i++) {
                String key = request.getKeyIndex(i);
                if (!resultKVSet.contains(key)) {
                    buffer.signList.updateRow(key, 0L);
                } else {
                    int l_count = 0, r_count = 0;
                    if (!runEvenList.get(i)) {
                        for (long val : resultKVSet.get(key)) {
                            if (val >= request.getThreshold(i))
                                r_count++;
                            else
                                l_count++;
                        }
                    } else {
                        for (long val : resultKVSet.get(key)) {
                            if (val <= request.getThreshold(i))
                                l_count++;
                            else
                                r_count++;
                        }
                    }
                    buffer.signList.updateRow(key, (long) (l_count - r_count));
                }
            }
            // LogUtils.debug(buffer.sharingCache.signList);
        } catch (Exception e) {
            responseBuilder.setCode(false).setMessage(LogUtils.buildErrorMessage(e));
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            responseObserver.onNext(responseBuilder.setCode(true).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void secureGroupByMax(RPCService.GroupByMaxRequest request, StreamObserver<RPCCommon.Status> responseObserver) {
        RPCCommon.Status.Builder responseBuilder = RPCCommon.Status.newBuilder();
        try {
            ResultKVSet resultKVSet = buffer.localResultTable;
            // LogUtils.debug();
            for (int i = 0; i < request.getKeyIndexCount(); i++) {
                String key = request.getKeyIndex(i);
                if (!resultKVSet.contains(key)) {
                    buffer.signList.updateRow(key, 0L);
                } else {
                    long sign = resultKVSet.get(key) >= request.getThreshold(i) ? random.nextInt(10) + 1 : 0;
                    buffer.signList.updateRow(key, sign);
                }
            }
        } catch (Exception e) {
            responseBuilder.setCode(false).setMessage(LogUtils.buildErrorMessage(e));
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            responseObserver.onNext(responseBuilder.setCode(true).build());
            responseObserver.onCompleted();
        }
    }
}
