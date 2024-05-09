package com.ada.federate.cache;

import com.ada.federate.utils.LogUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class ResultKVsSet {
    private Map<String, List<Long>> resultPair = new LinkedHashMap<>();
    public Integer rowNumber = 0;

    public ResultKVsSet() {

    }

    public Map<String, Long> getMedianValues() {
        Map<String, Long> medianValues = new LinkedHashMap<>();

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

            medianValues.put(key, median);
        }

        return medianValues;
    }

    public ResultKVsSet(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        for (String key : jsonObject.keySet()) {
            JSONArray arr = jsonObject.getJSONArray(key);
            for (int i = 0; i < arr.size(); i++) {
                addRow(key, arr.getLongValue(i));
            }
        }
    }


    public ResultKVsSet(ResultSet rs) throws Exception {
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
                addRow(key, val);
            }
        } else if (columnCount == 1) {

            while (rs.next()) {
                Long val = rs.getObject(1) == null ? 0L : Long.parseLong(rs.getObject(1).toString());
                // Long val = Long.parseLong(rs.getObject(1).toString());
                addRow("", val);
            }
        }
    }

    public String dump() {
        return JSONObject.toJSONString(resultPair);
    }

    public boolean contains(String key) {
        return resultPair.containsKey(key);
    }

    public Set<String> keySet() {
        return resultPair.keySet();
    }

    public List<Long> get(String key) {
        return resultPair.get(key);
    }

    public void addRow(String key, Long val) {
        if (!contains(key)) {
            resultPair.put(key, new ArrayList<>());
        }
        resultPair.get(key).add(val);
        rowNumber++;
    }

    public void addRow(String key, List<Long> val) {
        if (!contains(key)) {
            resultPair.put(key, new ArrayList<>());
        }
        resultPair.get(key).addAll(val);
        rowNumber++;
    }


    public void addAll(ResultKVsSet resultKVsSet) {
        for (String key : resultKVsSet.keySet()) {
            addRow(key, resultKVsSet.get(key));
        }
    }

    public static void main(String[] args) {
        ResultKVsSet resultKVSet = new ResultKVsSet();
        resultKVSet.addRow("shanghai", 1L);
        resultKVSet.addRow("shanghai", 2L);
        resultKVSet.addRow("shanghai", 5L);
        resultKVSet.addRow("shanghai", 6L);
        resultKVSet.addRow("shanghai", 7L);
        resultKVSet.addRow("guangzhou", 3L);
        // System.out.println(resultKVSet.dump());
        ResultKVsSet resultKVsSet = new ResultKVsSet(resultKVSet.dump());
        System.out.println(resultKVsSet.getMedianValues());

        // System.out.println(ResultKeyValuePair.parse(resultKeyValuePair.dump()).dump());
    }
}
