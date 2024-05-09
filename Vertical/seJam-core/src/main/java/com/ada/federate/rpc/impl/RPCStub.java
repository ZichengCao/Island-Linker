package com.ada.federate.rpc.impl;

import com.ada.federate.config.ClientConfig;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCService;
import com.ada.federate.rpc.SeJamGrpc;
import com.ada.federate.utils.LogUtils;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class RPCStub {
    private final SeJamGrpc.SeJamBlockingStub blockingStub;
    public String address;
    public Integer siloId;

    public ClientConfig.Endpoint endpoint;

    public RPCStub(String address, Integer siloId) {
        this.address = address;
        String ip = address.split(":")[0];
        int port = Integer.parseInt(address.split(":")[1]);
        // ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()；
        Channel channel =
                ManagedChannelBuilder.forAddress(ip, port).usePlaintext().maxInboundMessageSize(1024 * 1024 * 1024).build();
        this.siloId = siloId;
        this.blockingStub = SeJamGrpc.newBlockingStub(channel);
    }

    public RPCStub(String address) {
        this.address = address;
        Channel channel =
                ManagedChannelBuilder.forTarget(address).usePlaintext().maxInboundMessageSize(1024 * 1024 * 1024).build();
        this.blockingStub = SeJamGrpc.newBlockingStub(channel);
    }

    public RPCCommon.FinalResult aggregationADD(RPCCommon.SQLExpression expression) {
        try {
            return blockingStub.aggregationADD(expression);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.GroupIR groupByPlaintext(RPCCommon.SQLExpression expression) {
        try {
            return blockingStub.groupByPlaintext(expression);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.FinalResult groupByAggregationPlaintext(RPCCommon.PublicQueryMessage message) {
        try {
            return blockingStub.groupByAggregationPlaintext(message);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.GroupIR groupByORDER(RPCCommon.SQLExpression expression) {
        try {
            return blockingStub.groupByORDER(expression);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }


    public RPCCommon.RawIR aggregationORDER(RPCCommon.SQLExpression expression) {
        try {
            return blockingStub.aggregationORDER(expression);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.Status groupByADD(RPCCommon.SQLExpression expression) {
        try {
            return blockingStub.groupByADD(expression);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }


    public RPCCommon.AggregatedIR encryptedAggregateADD(RPCCommon.RawIR ir) {
        try {
            return blockingStub.encryptedAggregateADD(ir);
        } catch (StatusRuntimeException e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
        return null;
    }

    public RPCCommon.FinalResult decryptAggregatedORDER(RPCCommon.AggregatedIR ir) {
        try {
            return blockingStub.decryptAggregatedORDER(ir);
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

    public RPCCommon.Table publicQuery(RPCCommon.SQLExpression expression) {
        RPCCommon.QueryResult queryResult = null;
        try {
            queryResult = blockingStub.pubicQuery(expression);
            return queryResult.getTable();
        } catch (StatusRuntimeException e) {
            LogUtils.error(queryResult.getStatus().getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "FederateDBClient{" + "endpoints='" + address + '\'' + ", siloId=" + siloId + '}';
    }
}
