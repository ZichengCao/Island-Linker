package com.ada.federate;

import com.ada.federate.pojo.DriverConfig;
import com.ada.federate.utils.LogUtils;

import static com.ada.federate.utils.LogUtils.buildErrorMessage;

public class ServerApplication {
    private static final String configFile = "config.json";

    public static void main(String[] args) {
        try {
            DriverConfig config = new DriverConfig(configFile);
            int port = config.getServerPort();
            // RunningMonitor monitor = new RunningMonitor();
            // Thread monitorThread = new Thread(monitor);
            // monitorThread.start();
            RPCServer server = new RPCServer(port, config);
            LogUtils.info(config.getName() + " Server started...");
            server.start();
            server.blockUntilShutdown();
            // monitor.stop();
        } catch (Exception e) {
            LogUtils.error(buildErrorMessage(e));
        }
    }
}
