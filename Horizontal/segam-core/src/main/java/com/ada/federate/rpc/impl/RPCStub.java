package com.ada.federate.rpc.impl;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.rpc.SegamGrpc;
import com.ada.federate.utils.LogUtils;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * client <--> data owner
 */
public class RPCStub {
    private final SegamGrpc.SegamBlockingStub blockingStub;
    public String endpoints;
    public Integer siloId;


    public RPCStub(String endpoint, Integer siloId) {
        this.endpoints = endpoint;
        String ip = endpoint.split(":")[0];
        int port = Integer.parseInt(endpoint.split(":")[1]);
        // ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()；
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(ip, port).usePlaintext().maxInboundMessageSize(Integer.MAX_VALUE).build();
        this.siloId = siloId;

        this.blockingStub = SegamGrpc.newBlockingStub(channel);
    }

    public RPCStub(String endpoint) {
        this.endpoints = endpoint;
        Channel channel =
                ManagedChannelBuilder.forTarget(endpoint).usePlaintext().maxInboundMessageSize(1024 * 1024 * 1024).build();
        this.blockingStub = SegamGrpc.newBlockingStub(channel);
    }

    public RPCCommon.Status rpcPing(RPCService.PingRequest request) {
        try {
            return blockingStub.rpcPing(request);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.Status rpcHello(RPCService.HelloRequest request) {
        try {
            return blockingStub.rpcHello(request);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCService.SetUnionResponse privateSetUnion(RPCService.SetUnionRequest request) {
        RPCService.SetUnionResponse unionResponse = null;
        try {
            unionResponse = blockingStub.privateSetUnion(request);
            return unionResponse;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCService.CommandResponse command(RPCService.CommandRequest request) {
        RPCService.CommandResponse commandResponse = null;
        try {
            commandResponse = blockingStub.command(request);
            return commandResponse;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.Status sendSharing(RPCCommon.SSMessage ssMessage, int i) {
        RPCCommon.Status status = null;
        try {
            status = blockingStub.withWaitForReady().sendSharing(ssMessage);
            // if (LogUtils.DEBUG) LogUtils.debug(String.format("[++] sending sharing [%s] to silo %d", ssMessage.getShareValCount(), i));
            return status;
        } catch (StatusRuntimeException e) {
            // LogUtils.error(String.format("[!!] sending sharing [%s] to silo %d Failed.", ssMessage.getShareValCount(), i));
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.Status secureGroupByMedian(RPCService.GroupByMedianRequest request) {
        RPCCommon.Status status = null;
        try {
            status = blockingStub.secureGroupByMedian(request);
            return status;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));

        }
        return null;
    }

    public RPCCommon.Status secureGroupByMax(RPCService.GroupByMaxRequest request) {
        RPCCommon.Status status = null;
        try {
            status = blockingStub.secureGroupByMax(request);
            return status;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));

        }
        return null;
    }

    public RPCCommon.SSMessage secureGroupSummationBySS(RPCService.GroupBySumSSRequest request) {
        RPCCommon.SSMessage ssMessage = null;
        try {
            ssMessage = blockingStub.secureGroupBySumSS(request);
            return ssMessage;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.Status privacyQuery(RPCCommon.SQLExpression expression) {
        RPCCommon.Status status = null;
        try {
            status = blockingStub.privateQuery(expression);
            return status;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.RPCResult publicQuery(RPCCommon.SQLExpression expression) {
        RPCCommon.RPCResult RPCResult = null;
        try {
            RPCResult = blockingStub.pubicQuery(expression);
            return RPCResult;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }


    public RPCCommon.Status batchQuery(RPCCommon.SQLMessage expression) {
        RPCCommon.Status status = null;
        try {
            status = blockingStub.batchQuery(expression);
            return status;
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    @Override
    public String toString() {
        return "FederateDBClient{" + "endpoints='" + endpoints + '\'' + ", siloId=" + siloId + '}';
    }
}
