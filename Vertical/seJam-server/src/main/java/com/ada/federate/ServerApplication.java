package com.ada.federate;

import com.ada.federate.config.DriverConfig;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.PathUtils;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.ada.federate.utils.LogUtils.buildErrorMessage;

public class ServerApplication {
    private static final String configFile = "config.json";
    public static final DriverConfig config;

    static {
        try {
            String configPath = PathUtils.getRealPath(configFile);
            String jsonString = new String(Files.readAllBytes(Paths.get(configPath)));
            config = JSONObject.parseObject(jsonString, DriverConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            int port = config.getServerPort();
            RPCServer server = new RPCServer(port, config);
            LogUtils.info(config.getName() + " Server started...");
            server.start();
            server.blockUntilShutdown();
            // 停止监控线程
            // monitor.stop();
        } catch (Exception e) {
            LogUtils.error(buildErrorMessage(e));
        }
    }
}
