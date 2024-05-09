package com.ada.federate.pojo;

import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.PathUtils;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClientConfig {

    private List<String> endpoints = new ArrayList<>();

    public ClientConfig(List<String> endpoints) {
        this.endpoints = endpoints;
    }

    public ClientConfig(String filePath) throws IOException {
        String configPath = PathUtils.getRealPath(filePath);
        String jsonString = new String(Files.readAllBytes(Paths.get(configPath)));
        ClientConfig clientConfig = JSONObject.parseObject(jsonString, ClientConfig.class);
        LogUtils.info(String.format("Configuration reading completed, a total of [%d] data owners.", clientConfig.endpoints.size()));
        this.endpoints.addAll(clientConfig.endpoints);
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }
}
