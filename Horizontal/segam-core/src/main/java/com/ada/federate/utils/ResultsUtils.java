package com.ada.federate.utils;

import com.ada.federate.cache.ResultKVSet;
import com.ada.federate.rpc.RPCCommon;
import com.alibaba.fastjson2.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ResultsUtils {

    public static ResultKVSet assembleResultTable(List<RPCCommon.RPCResult> resultKVSetList, RPCCommon.SQLExpression expression) {
        Map<String, Long> mergeResultKVPS = new LinkedHashMap<>();
        // ResultKVP.Builder builder = ResultKVP.newBuilder();
        if (expression.getAggField().toLowerCase().contains("sum") || expression.getAggField().toLowerCase().contains(
                "count")) {

            for (RPCCommon.RPCResult table : resultKVSetList) {
                for (int i = 0; i < table.getKeyCount(); i++) {
                    String key = table.getKey(i);
                    Long value = table.getValue(i);
                    Long tempVal = mergeResultKVPS.getOrDefault(key, 0L);
                    mergeResultKVPS.put(key, tempVal + value);
                }
            }

        } else if (expression.getAggField().toLowerCase().contains("max")) {

            for (RPCCommon.RPCResult table : resultKVSetList) {
                for (int i = 0; i < table.getKeyCount(); i++) {
                    String key = table.getKey(i);
                    Long value = table.getValue(i);
                    Long tempVal = mergeResultKVPS.getOrDefault(key, (long) Integer.MIN_VALUE);
                    if (value > tempVal)
                        mergeResultKVPS.put(key, value);
                }
            }
        } else if (expression.getAggField().toLowerCase().contains("median")) {
            Map<String, List<Long>> resultPair = new LinkedHashMap<>();

            for (RPCCommon.RPCResult table : resultKVSetList) {
                for (int i = 0; i < table.getKeyCount(); i++) {
                    String key = table.getKey(i);
                    Long tempVal = table.getValue(i);
                    if (!resultPair.containsKey(key)) {
                        resultPair.put(key, new ArrayList<>());
                    }
                    resultPair.get(key).add(tempVal);
                }
            }

            for (Map.Entry<String, List<Long>> entry : resultPair.entrySet()) {
                String key = entry.getKey();
                List<Long> values = entry.getValue();
                int len = values.size();
                // System.out.println(values);

                Collections.sort(values);

                long median;
                if (len % 2 == 0) {
                    median = (values.get(len / 2 - 1) + values.get(len / 2)) / 2;
                } else {
                    median = values.get(len / 2);
                }

                mergeResultKVPS.put(key, median);
            }

        }
        return new ResultKVSet(mergeResultKVPS);
    }


    public static ResultKVSet array2QueryResult(long[] results, List<String> fullKeyList) throws Exception {
        // if (results.length != fullKeyList.size()) {
        //     throw new Exception(String.format("Error: results size: [%d], full key size: [%d] ", results.length, fullKeyList.size()));
        // }
        ResultKVSet resultKVSet = new ResultKVSet();
        for (int i = 0; i < results.length; i++) {
            resultKVSet.addRow(fullKeyList.size() == 0 ? "" : fullKeyList.get(i), results[i]);
        }
        return resultKVSet;
    }


    public static RPCCommon.RPCResult.Builder resultSet2RPCResult(ResultSet rs) throws SQLException {
        List<String> keyList = new ArrayList<>();
        List<Long> valueList = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        if (columnCount == 2) {
            //  dimension >= 1
            while (rs.next()) {
                // List<Object> list;
                String key = rs.getObject(2).toString();
                String str = rs.getObject(1).toString();
                int decimalIndex = str.indexOf(".");
                String integerPart = (decimalIndex != -1) ? str.substring(0, decimalIndex) : str;
                Long val = Long.parseLong(integerPart);
                keyList.add(key);
                valueList.add(val);
            }
        } else if (columnCount == 1) {

            while (rs.next()) {
                Long val = rs.getObject(1) == null ? 0L : Long.parseLong(rs.getObject(1).toString());
                // Long val = Long.parseLong(rs.getObject(1).toString());
                keyList.add("");
                valueList.add(val);
            }
        }
        return RPCCommon.RPCResult.newBuilder().addAllKey(keyList).addAllValue(valueList);
    }

    public static String map2JsonString(Map<String, List<String>> fullKeyListMap) {
        // Map<String, String> tempMap = new HashMap<>();
        // for (String key : fullKeyListMap.keySet()) {
        //     List<String> tempList = fullKeyListMap.get(key);
        //     tempMap.put(key,String.join(",", tempList)) ;
        // }
        return JSONObject.toJSONString(fullKeyListMap);
    }

    public static void main(String[] args) {
        Map<String, List<String>> fullKeyListMap = new HashMap<>();
        fullKeyListMap.put("city", Arrays.asList("shanghai", "beijing", "nanjing"));
        fullKeyListMap.put("year", Arrays.asList("1892", "1998", "2562"));

        System.out.println();
        JSONObject jsonObject = JSONObject.parse(map2JsonString(fullKeyListMap));
        System.out.println(jsonObject);
        for (String key : jsonObject.keySet()) {
            System.out.println(jsonObject.getJSONArray(key));
        }
    }
}
