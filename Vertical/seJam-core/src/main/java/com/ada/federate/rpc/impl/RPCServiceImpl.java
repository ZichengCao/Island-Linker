package com.ada.federate.rpc.impl;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.rpc.SeJamGrpc;
import com.ada.federate.sql.SQLBuilder;
import com.ada.federate.sql.SQLExecutor;
import com.ada.federate.utils.LogUtils;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// import static com.ada.federate.TestApplication.timeframeList;

public abstract class RPCServiceImpl extends SeJamGrpc.SeJamImplBase {

    protected long startTime, endTime;

    protected RPCStub nextRPCStub = null;
    protected List<RPCStub> RPCStubList = new ArrayList<>();
    protected int siloId;
    protected int siloSize;

    // thread pool for secret sharing
    public static ExecutorService executorService;

    @Override
    public void rpcHello(RPCService.HelloRequest request, StreamObserver<RPCCommon.Status> responseObserver) {
        RPCCommon.Status status = RPCCommon.Status.newBuilder().setCode(true).build();
        RPCService.HelloRequest.Builder requestBuilder = request.toBuilder().clone();
        // long startTime, endTime;
        // startTime = System.currentTimeMillis();
        try {
            List<String> endpointList = request.getEndpointList();
            siloId = request.getSiloId();
            requestBuilder.setSiloId(siloId + 1);
            siloSize = endpointList.size();
            executorService = Executors.newFixedThreadPool(siloSize + 1);
            for (int i = 0; i < siloSize; i++) {
                RPCStubList.add(new RPCStub(endpointList.get(i), i));
            }
            LogUtils.debug(String.format("RPC hello: total [%d] clients, I'm No. [%d]", siloSize, siloId));
            nextRPCStub = RPCStubList.get((siloId + 1) % siloSize);
            int nextIndex = (request.getIndex() + 1);
            if (nextIndex == siloSize) requestBuilder.setRound(request.getRound() + 1);
            nextIndex = nextIndex % siloSize;
            if (requestBuilder.getRound() <= 1 && nextIndex < siloSize) {
                if (LogUtils.DEBUG) LogUtils.debug(String.format("rpc hello: next Client (%s), siloId (%s)", nextRPCStub.address, siloId));
                RPCCommon.Status nextStatus = nextRPCStub.rpcHello(requestBuilder.setIndex(nextIndex).build());
                status = status.toBuilder().setCode(status.getCode() && nextStatus.getCode()).build();
            }
        } catch (Exception e) {
            status = status.toBuilder().setCode(false).build();
            LogUtils.error(LogUtils.buildErrorMessage(e));
        } finally {
            // endTime = System.currentTimeMillis();
            // timeframeList.put("hello", endTime - startTime);
            responseObserver.onNext(status);
            responseObserver.onCompleted();
        }
    }


}
