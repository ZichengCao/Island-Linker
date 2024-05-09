package com.ada.federate.cache;

import com.alibaba.fastjson2.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ResultColumn {
    public String fieldName;
    public List<String> valList = new ArrayList<>();


    public static ResultColumn resultSet2ResultColumn(ResultSet rs) throws SQLException {
        ResultColumn resultColumn = new ResultColumn();

        ResultSetMetaData metaData = rs.getMetaData();
        // LogUtils.debug("metaData.getColumnLabel(1)");
        resultColumn.fieldName = metaData.getColumnName(1);
        // System.out.println(resultColumn.fieldName);
        while (rs.next()) {
            String value = rs.getString(1);
            resultColumn.add(value);
        }
        return resultColumn;
    }


    public static List<ResultColumn> resultSet2ResultColumnList(ResultSet rs) throws SQLException {
        List<ResultColumn> resultColumnList = new ArrayList<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            resultColumnList.add(new ResultColumn());
            resultColumnList.get(i).fieldName = metaData.getColumnLabel(i + 1);
        }


        while (rs.next()) {

            for (int i = 1; i <= columnCount; i++) {
                String value = rs.getString(i);
                resultColumnList.get(i - 1).add(value);
            }
        }
        return resultColumnList;
    }

    public String get(int index) {
        return valList.get(index);
    }

    public void add(String val) {
        valList.add(val);
    }

    public void addAll(Collection<String> collection) {
        valList.addAll(collection);
    }

    public String dump() {
        return String.format("{\"%s\":[%s]}", fieldName,
                valList.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(","))
        );
    }

    public static List<ResultColumn> parse(String jsonStr) {
        List<ResultColumn> resultColumnList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        for (String key : jsonObject.keySet()) {
            ResultColumn resultColumn = new ResultColumn();
            resultColumn.fieldName = key;
            List<String> jsonArray = jsonObject.getList(key, String.class);
            resultColumn.addAll(jsonArray);
            resultColumnList.add(resultColumn);
        }
        return resultColumnList;
    }

    public static String batchDump(List<ResultColumn> resultColumnList) {
        //String.format("{\"%s\":[%s]}", fieldName,
        //                 valList.stream().map(x -> "\"" + x + "\"").collect(Collectors.joining(","))
        List<String> stringList = resultColumnList.stream().map(
                x -> String.format("\"%s\":[%s]", x.fieldName,
                        x.valList.stream().map(y -> "\"" + y + "\"").collect(Collectors.joining(","))
                )).collect(Collectors.toList());

        return "{" + String.join(",", stringList) + "}";
    }


    public static void main(String[] args) {

    }
}
