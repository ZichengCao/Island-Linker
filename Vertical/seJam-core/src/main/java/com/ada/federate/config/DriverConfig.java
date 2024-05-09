package com.ada.federate.config;

import com.ada.federate.utils.PathUtils;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DriverConfig {
    String name;
    String driver;
    String user;
    String password;
    String url;
    Integer serverPort;

    public DriverConfig(String name, String driver, String user, String password, String url, Integer port) {
        this.name = name;
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.url = url;
        this.serverPort = port;
    }

    public DriverConfig(String filePath) throws IOException {
        String configPath = PathUtils.getRealPath(filePath);
        String jsonString = new String(Files.readAllBytes(Paths.get(configPath)));
        DriverConfig driverConfig = JSONObject.parseObject(jsonString, DriverConfig.class);
        this.name = driverConfig.name;
        this.driver = driverConfig.driver;
        this.user = driverConfig.user;
        this.password = driverConfig.password;
        this.url = driverConfig.url;
        this.serverPort = driverConfig.serverPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
}
