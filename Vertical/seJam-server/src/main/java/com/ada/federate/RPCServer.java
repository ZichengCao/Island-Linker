package com.ada.federate;

import com.ada.federate.rpc.SeJamGrpc;
import com.ada.federate.service.PostgresqlService;
import com.ada.federate.config.DriverConfig;
import com.ada.federate.utils.LogUtils;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RPCServer {
    protected final int port;
    protected final Server server;

    public RPCServer(int port, DriverConfig config)
            throws SQLException, ClassNotFoundException, IOException {
        this.port = port;
        SeJamGrpc.SeJamImplBase service = new PostgresqlService(config);
        server = ServerBuilder.forPort(port)
                .addService(service)
                .maxInboundMessageSize(Integer.MAX_VALUE)
                .build();
    }

    public void start() throws IOException {
        server.start();
        LogUtils.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                RPCServer.this.shutdown();
            } catch (InterruptedException e) {
                LogUtils.error(LogUtils.buildErrorMessage(e));
            } finally {
                LogUtils.info("server shut down");
            }
        }));
    }

    public void shutdown() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    protected void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
