package com.ada.federate.cache;

import com.ada.federate.rpc.RPCCommon;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.stream.Collectors;


public class ResultKVSet {

    // use linkedHashMap to guarantee the order of keySet is same each time.
    private Map<String, Long> resultPair = new LinkedHashMap<>();


    public Integer rowNumber = 0;


    public ResultKVSet() {

    }

    public ResultKVSet(Map<String, Long> map) {
        resultPair = map;
    }

    public ResultKVSet(ResultSet rs) throws Exception {
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

    public boolean contains(String key) {
        return resultPair.containsKey(key);
    }

    public Set<String> keySet() {
        return resultPair.keySet();
    }


    /***
     *
     * @param key
     * @return first value of key
     */
    public Long get(String key) {
        return resultPair.get(key);
    }

    public Long getOrDefault(String key, Long defaultValue) {
        // keySetMap.getOrDefault()
        if (contains(key)) return get(key);
        else return defaultValue;
    }


    public void addRow(String key, Long val) {
        if (!contains(key)) {
            resultPair.put(key, val);
            rowNumber++;
        } else {
            long oldVal = resultPair.get(key);
            resultPair.put(key, (val + oldVal) / 2);
            rowNumber++;
        }
    }

    /***
     * if key not in result table --> add value
     * else --> update value of key
     * @param key
     * @param val
     */
    public void updateRow(String key, Long val) {
        if (!contains(key)) {
            addRow(key, val);
        } else {
            resultPair.put(key, val);
        }
    }

    @Override
    public String toString() {
        // return dump(this);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int i = 0;
        for (String key : resultPair.keySet()) {
            sb.append("<").append(key).append(", ").append(resultPair.get(key)).append(">");
            if (i != resultPair.keySet().size() - 1) {
                sb.append(",");
            }
            i++;
        }

        sb.append("}");
        return sb.toString();
    }

    public void printResultTable(RPCCommon.SQLExpression sqlExpression, int maxPrintLength) {
        StringBuilder sb = new StringBuilder();

        for (String dimensionName : sqlExpression.getDimensionsList()) {
            if (dimensionName.contains(" as "))
                sb.append(String.format("%-20s", dimensionName.split(" as ")[1]));
            else
                sb.append(String.format("%-20s", dimensionName));
        }
        String aggFieldName = sqlExpression.getAggField();
        if (aggFieldName.contains(" as "))
            sb.append(String.format("%-20s", aggFieldName.split(" as ")[1]));
        else
            sb.append(String.format("%-20s", aggFieldName));
        sb.append("\n");
        sb.append(StringUtils.repeat('-', (sqlExpression.getDimensionsCount() + 1) * 20));
        sb.append("\n");

        for (String key : resultPair.keySet()) {
            // if (get(key) == 0) continue;
            maxPrintLength--;
            if (maxPrintLength < 0) break;
            // String realVal = fullKeyListMap.get(key);
            String[] dimensionVal = key.split("_");
            for (String val : dimensionVal) {
                if (!val.isEmpty())
                    sb.append(String.format("%-20s", val));
            }
            sb.append(String.format("%-20d", get(key)));
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void batchPrintResultTable(RPCCommon.SQLExpression sqlExpression, ResultKVSet... resultKVSetList) {
        batchPrintResultTable(sqlExpression, Arrays.stream(resultKVSetList).collect(Collectors.toList()));
    }

    public static void batchPrintResultTable(RPCCommon.SQLExpression sqlExpression, List<ResultKVSet> resultKVSetList) {
        StringBuilder sb = new StringBuilder();
        List<String> dimensionList = sqlExpression.getDimensionsList();
        int columnNumber = sqlExpression.getDimensionsCount() + 1;
        // System.out.println(dimensionList);
        for (ResultKVSet resultKVSet : resultKVSetList) {
            for (String dimensionName : dimensionList) {
                if (dimensionName.contains(" as "))
                    sb.append(String.format("%-20s", dimensionName.split(" as ")[1]));
                else
                    sb.append(String.format("%-20s", dimensionName));
            }
            String aggFieldName = sqlExpression.getAggField();
            if (aggFieldName.contains(" as "))
                sb.append(String.format("%-20s", aggFieldName.split(" as ")[1]));
            else
                sb.append(String.format("%-20s", aggFieldName));
        }

        sb.append("\n");
        sb.append(StringUtils.repeat('-', resultKVSetList.size() * 20 * columnNumber));
        sb.append("\n");

        Set<String> fullKeyList = new HashSet<>();
        for (ResultKVSet table : resultKVSetList) {
            fullKeyList.addAll(table.keySet());
        }
        int k = 0;
        for (String key : fullKeyList) {
            k++;
            if (k >= 20) break;
            for (int i = 0; i < resultKVSetList.size(); i++) {
                ResultKVSet resultKVSet = resultKVSetList.get(i);
                if (resultKVSet.contains(key)) {
                    // FIXME
                    // String realVal = fullKeyListMap.get(key);
                    String[] dimensionVal = key.split("_");
                    for (String val : dimensionVal) {
                        if (!val.isEmpty())
                            sb.append(String.format("%-20s", val));
                    }
                    sb.append(String.format("%-20d", resultKVSet.get(key)));
                } else {
                    sb.append(StringUtils.repeat(String.format("%-20s", "-"), columnNumber));
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public String dump() {
        return JSONObject.toJSONString(resultPair);
    }


    // public static ResultKVSet assembleResultKVTable(List<RPCResult> RPCResults) {
    //     ResultKeyValuesSet mergeResultKVSet = new ResultKeyValuesSet();
    //
    //     for (RPCResult table : RPCResults) {
    //         mergeResultKVSet.addAll(table);
    //     }
    //
    //     return new ResultKVSet(mergeResultKVSet.getMedianValues());
    // }
    //
    // public static ResultKVSet assembleResultTable(List<RPCResult> resultKVSetList, SQLExpression expression) {
    //     ResultKVSet mergeResultKVSet = new ResultKVSet();
    //     Set<String> fullKeyList = new HashSet<>();
    //     for (ResultKVSet table : resultKVSetList) {
    //         fullKeyList.addAll(table.keySet());
    //     }
    //
    //     if (expression.getAggField().toLowerCase().contains("sum") || expression.getAggField().toLowerCase().contains(
    //             "count")) {
    //         for (String key : fullKeyList) {
    //             int tempSum = 0;
    //             for (ResultKVSet table : resultKVSetList) {
    //                 tempSum += table.getOrDefault(key, 0);
    //             }
    //             mergeResultKVSet.addRow(key, tempSum);
    //         }
    //     } else if (expression.getAggField().toLowerCase().contains("max")) {
    //         for (String key : fullKeyList) {
    //             int tempMax = Integer.MIN_VALUE;
    //             for (ResultKVSet table : resultKVSetList) {
    //                 if (table.contains(key) && table.get(key) > tempMax) {
    //                     tempMax = table.get(key);
    //                 }
    //             }
    //             mergeResultKVSet.addRow(key, tempMax);
    //         }
    //     } else if (expression.getAggField().toLowerCase().contains("median")) {
    //         return resultKVSetList.get(0);
    //     }
    //     return mergeResultKVSet;
    // }

    public void clean() {
        resultPair.clear();
        rowNumber = 0;
    }

    public static void main(String[] args) {
        ResultKVSet resultKVSet = new ResultKVSet();
        resultKVSet.addRow("shanghai", 1L);
        resultKVSet.addRow("beijing", 2L);
        resultKVSet.addRow("guangzhou", 3L);
        // System.out.println(resultKeyValuePair.dump());


        // System.out.println(ResultKeyValuePair.parse(resultKeyValuePair.dump()).dump());
    }
}

