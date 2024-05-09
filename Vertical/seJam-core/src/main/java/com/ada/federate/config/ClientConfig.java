package com.ada.federate.config;

import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.PathUtils;
import com.alibaba.fastjson2.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientConfig {

    private List<Endpoint> endpoints = new ArrayList<>();


    public static class Endpoint {
        String address;

        String schema;
        Table table;


        public Endpoint() {

        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }


        public Table getTable() {
            return table;
        }

        public void setTable(Table table) {
            this.table = table;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Endpoint{" +
                    "address='" + address + '\'' +
                    ", schema='" + schema + '\'' +
                    ", table=" + table +
                    '}';
        }
    }

    public static class Table {
        String name;
        List<String> attributes;

        public Table() {
        }


        public boolean belongs(String expression) {
            for (String s : attributes) {
                if (expression.contains(s))
                    return true;

            }
            return false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<String> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "name='" + name + '\'' +
                    ", attributes=" + attributes +
                    '}';
        }
    }

    public ClientConfig(String filePath) throws IOException {
        String configPath = PathUtils.getRealPath(filePath);
        String jsonString = new String(Files.readAllBytes(Paths.get(configPath)));
        List<Endpoint> endpointList = JSONArray.parseArray(jsonString, Endpoint.class);
        endpoints.addAll(endpointList);
        LogUtils.info(String.format("读取配置完成，共 [%d] 个数据方", endpoints.size()));
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }

    public List<String> getAddressList() {
        return endpoints.stream().map(x -> x.address).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "endpoints=" + endpoints +
                '}';
    }

    public static void main(String[] args) throws IOException {
        ClientConfig config = new ClientConfig("config.json");
        System.out.println(config);
    }
}
