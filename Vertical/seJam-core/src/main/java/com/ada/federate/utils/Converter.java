package com.ada.federate.utils;

import com.ada.federate.ope.OPE;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.secure.Paillier;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Converter {

    public static RPCCommon.GroupIR convertResultSetToGroupIR(ResultSet resultSet, boolean hashFlag) throws SQLException {
        List<String> keys = new ArrayList<>();
        List<RPCCommon.IDs> idsList = new ArrayList<>();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            String key = resultSet.getString(1);
            String[] ids = resultSet.getString(2).split("_");
            if (hashFlag) {
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = HashUtils.md5(ids[i]);
                    // ids[i] = ids[i];
                }
            }
            keys.add(key);
            idsList.add(RPCCommon.IDs.newBuilder().addAllId(Arrays.stream(ids).collect(Collectors.toList())).build());
        }
        return RPCCommon.GroupIR.newBuilder().addAllKey(keys).addAllId(idsList).build();
    }

    public static RPCCommon.RawIR convertResultSet2RawIRORDER(ResultSet resultSet, OPE ope) throws SQLException {
        RPCCommon.RawIR.Builder builder = RPCCommon.RawIR.newBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Integer val = resultSet.getInt(1);
            String id = resultSet.getString(2);
            // builder.addId(id);
            builder.addId(HashUtils.md5(id));
            builder.addVal(ope.encrypt(val.longValue()).toString());
        }
        return builder.build();
    }

    public static RPCCommon.RawIR convertResultSet2RawIRADD(ResultSet resultSet, Paillier paillier) throws SQLException {
        RPCCommon.RawIR.Builder builder = RPCCommon.RawIR.newBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            Integer val = resultSet.getInt(1);
            String id = resultSet.getString(2);
            builder.addId(id);
            builder.addVal(paillier.encrypt(new BigInteger(val.toString())).toString());
        }
        return builder.build();
    }

    public static RPCCommon.FinalResult encryptedIR2Table(RPCCommon.AggregatedIR encryptedIR, Paillier paillier) {
        List<String> keyList = encryptedIR.getKeyList();
        List<String> encryptedValList = encryptedIR.getValList();
        List<String> valList = encryptedValList.stream().map(x -> paillier.decrypt(new BigInteger(x)).toString()).collect(Collectors.toList());
        return RPCCommon.FinalResult.newBuilder().addAllKey(keyList).addAllVal(valList).build();
    }

    public static HashMap<String, Integer> convertResultSet2MapADD(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        HashMap<String, Integer> map = new HashMap<>();
        while (resultSet.next()) {
            Integer val = resultSet.getInt(1);
            String id = resultSet.getString(2);
            if (map.containsKey(id)) {
                Integer origin = map.get(id);
                map.put(id, origin + val);
            } else {
                map.put(id, val);
            }
        }
        return map;
    }

    public static HashMap<String, Integer> convertResultSet2MapORDER(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        HashMap<String, Integer> map = new HashMap<>();
        while (resultSet.next()) {
            Integer val = resultSet.getInt(1);
            String id = resultSet.getString(2);
            if (map.containsKey(id)) {
                Integer origin = map.get(id);
                map.put(id, origin > val ? origin : val);
            } else {
                map.put(id, val);
            }
        }
        return map;
    }

    public static Map<String, List<String>> convertGroupIR2Map(RPCCommon.GroupIR groupIR) {
        Map<String, List<String>> hashMap = new HashMap<>();
        List<String> keys = groupIR.getKeyList();
        List<RPCCommon.IDs> idsList = groupIR.getIdList();

        // 遍历keys和idsList
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            RPCCommon.IDs ids = idsList.get(i);
            List<String> idList = ids.getIdList();
            hashMap.put(key, idList);
        }

        return hashMap;
    }
}
