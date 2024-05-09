package com.ada.federate.service;

import com.ada.federate.config.DriverConfig;
import com.ada.federate.ope.OPE;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.impl.RPCServiceImpl;
import com.ada.federate.secure.Paillier;
import com.ada.federate.secure.PrimeGenerator;
import com.ada.federate.sql.SQLBuilder;
import com.ada.federate.sql.SQLExecutor;
import com.ada.federate.utils.Converter;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.ResultsUtils;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

// import static com.ada.federate.TestApplication.timeframeList;

public class PostgresqlService extends RPCServiceImpl {

    private SQLExecutor executor;

    public PostgresqlService(DriverConfig config) throws ClassNotFoundException, SQLException, IOException {
        Class.forName(config.getDriver());
        // Properties properties = new Properties();
        // properties.setProperty("user", config.getUser());
        // properties.setProperty("password", config.getPassword());
        // properties.setProperty("client_name", config.getName());
        Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        // Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        executor = new SQLExecutor(conn);
    }


    @Override
    public void groupByAggregationPlaintext(RPCCommon.PublicQueryMessage request, StreamObserver<RPCCommon.FinalResult> responseObserver) {
        RPCCommon.FinalResult.Builder builder = RPCCommon.FinalResult.newBuilder();
        try {
            RPCCommon.GroupIR groupIR = nextRPCStub.groupByPlaintext(request.getGroupExpression());
            Map<String, List<String>> groupResults = Converter.convertGroupIR2Map(groupIR);

            String sql = SQLBuilder.buildAggregationSQL(request.getAggregateExpression());
            ResultSet rs = executor.executeSql(sql);

            if (request.getFuncType() == RPCCommon.FuncType.SUM) {
                Map<String, Integer> aggregationResult = Converter.convertResultSet2MapADD(rs);
                // 遍历groupResults中的每个分组和其对应的id列表
                for (Map.Entry<String, List<String>> entry : groupResults.entrySet()) {
                    String groupAttribute = entry.getKey(); // 分组属性值
                    List<String> idList = entry.getValue(); // 分组属性值对应的一组id

                    int sum = 0;

                    // 遍历当前分组的每个id，从aggregationResult中获取其对应的聚合属性值并累加
                    for (String id : idList) {
                        Integer aggregationValue = aggregationResult.get(id);
                        if (aggregationValue != null) {
                            sum += aggregationValue;
                        }
                    }
                    builder.addKey(groupAttribute).addVal(String.valueOf(sum));
                }
            } else {
                Map<String, Integer> aggregationResult = Converter.convertResultSet2MapORDER(rs);
                // 遍历groupResults中的每个分组和其对应的id列表
                for (Map.Entry<String, List<String>> entry : groupResults.entrySet()) {
                    String groupAttribute = entry.getKey(); // 分组属性值
                    List<String> idList = entry.getValue(); // 分组属性值对应的一组id
                    List<Integer> values = new ArrayList<>();
                    int max = Integer.MIN_VALUE; // 初始化最大值为最小整数值

                    // 遍历当前分组的每个id，从aggregationResult中获取其对应的聚合属性值，并更新最大值
                    for (String id : idList) {
                        Integer aggregationValue = aggregationResult.get(id);
                        if (aggregationValue != null) {
                            max = Math.max(max, aggregationValue);
                            values.add(aggregationValue);
                        }
                    }

                    if (request.getFuncType() == RPCCommon.FuncType.MEDIAN) {
                        Collections.sort(values);
                        int size = values.size();
                        int median = Integer.MIN_VALUE;
                        if (size > 0) {
                            if (size == 1) {
                                median = values.get(0);
                            } else if (size % 2 == 0) {
                                median = (values.get(size / 2) + values.get((size / 2) - 1)) / 2;
                            } else {
                                median = values.get(size / 2);
                            }
                            builder.addKey(groupAttribute).addVal(String.valueOf(median));
                        }
                    } else {
                        if (max != Integer.MIN_VALUE)
                            builder.addKey(groupAttribute).addVal(String.valueOf(max));
                    }

                }
            }

        } catch (
                Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
        } finally {
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void groupByPlaintext(RPCCommon.SQLExpression expression, StreamObserver<RPCCommon.GroupIR> responseObserver) {
        RPCCommon.GroupIR groupIR = null;
        try {
            String querySQL = SQLBuilder.buildGroupSQL(expression);
            ResultSet rs = executor.executeSql(querySQL);
            groupIR = Converter.convertResultSetToGroupIR(rs, false);
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
        } finally {
            responseObserver.onNext(groupIR);
            responseObserver.onCompleted();
        }
    }

    Map<String, Object> buffer = new HashMap<>();

    @Override
    public void groupByADD(RPCCommon.SQLExpression request, StreamObserver<RPCCommon.Status> responseObserver) {
        String querySQL = SQLBuilder.buildGroupSQL(request);
        RPCCommon.Status.Builder builder = RPCCommon.Status.newBuilder();
        try {
            ResultSet rs = executor.executeSql(querySQL);
            RPCCommon.GroupIR groupIR = Converter.convertResultSetToGroupIR(rs, request.getHashFlag());
            buffer.put(String.valueOf(request.getUuid()), groupIR);
            builder.setCode(true);
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
            builder.setCode(false).setMessage(temp);
        } finally {
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void groupByORDER(RPCCommon.SQLExpression request, StreamObserver<RPCCommon.GroupIR> responseObserver) {
        String querySQL = SQLBuilder.buildGroupSQL(request);
        RPCCommon.GroupIR groupIR = null;
        try {
            ResultSet rs = executor.executeSql(querySQL);
            groupIR = Converter.convertResultSetToGroupIR(rs, request.getHashFlag());
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
        } finally {
            responseObserver.onNext(groupIR);
            responseObserver.onCompleted();
        }
    }

    /**
     * paillier 同态加
     *
     * @param c1
     * @param c2
     * @param n  公钥
     * @return
     */
    public static BigInteger paillierAdd(BigInteger c1, BigInteger c2, BigInteger n) {
        // LogUtils.debug(c1);
        // LogUtils.debug(c2);
        // LogUtils.debug(n);
        return c1.multiply(c2).mod(n.multiply(n));
    }

    @Override
    public void encryptedAggregateADD(RPCCommon.RawIR rawIR, StreamObserver<RPCCommon.AggregatedIR> responseObserver) {
        RPCCommon.AggregatedIR.Builder builder = RPCCommon.AggregatedIR.newBuilder();
        try {
            // LogUtils.debug(rawIR.getValList());
            RPCCommon.GroupIR groupIR = (RPCCommon.GroupIR) buffer.get(rawIR.getUuid());

            Map<String, BigInteger> aggregatedValues = new HashMap<>();

            List<String> keys = groupIR.getKeyList();
            List<RPCCommon.IDs> idGroups = groupIR.getIdList();

            // Aggregate values
            for (int i = 0; i < keys.size(); i++) {
                List<String> ids = idGroups.get(i).getIdList();
                boolean firstFlag = true;
                BigInteger sum = BigInteger.ZERO;
                for (String id : ids) {
                    for (int j = 0; j < rawIR.getIdCount(); j++) {
                        if (rawIR.getId(j).equals(id)) {
                            BigInteger value = new BigInteger(rawIR.getVal(j));
                            if (firstFlag) {
                                sum = value;
                                firstFlag = false;
                            } else {
                                sum = paillierAdd(sum, value, new BigInteger(rawIR.getPublicKey()));
                            }
                        }
                    }
                }
                if (sum.compareTo(BigInteger.ZERO) != 0) {
                    // LogUtils.debug(keys.get(i) + " " + sum);
                    aggregatedValues.put(keys.get(i), sum);
                }
            }
            // Build FinalResult message
            for (String key : aggregatedValues.keySet()) {
                builder.addKey(key);
                builder.addVal(aggregatedValues.get(key).toString());
            }
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
            // builder.setCode(false).setMessage(temp);
        } finally {
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    private static final Paillier paillier;

    static {
        int PRIME_BIT = 512;
        paillier = new Paillier(PrimeGenerator.generatePrime(PRIME_BIT), PrimeGenerator.generatePrime(PRIME_BIT));
        // paillier = new Paillier(new BigInteger("17"), new BigInteger("19"));
    }


    @Override
    public void aggregationADD(RPCCommon.SQLExpression expression, StreamObserver<RPCCommon.FinalResult> responseObserver) {

        String querySQL = SQLBuilder.buildADDAggregationSQL(expression);
        // String querySQL = SQLBuilder.buildAggregationSQL(expression);

        RPCCommon.FinalResult result = null;
        try {
            ResultSet rs = executor.executeSql(querySQL);
            // 查询并加密
            RPCCommon.RawIR rawIR = Converter.convertResultSet2RawIRADD(rs, paillier).toBuilder().setUuid(String.valueOf(expression.getUuid()))
                    .setPublicKey(paillier.getN().toString()).build();
            // LogUtils.debug(rawIR.getValList());
            // 调用分租方 RPC 接口，进行按组聚合
            RPCCommon.AggregatedIR aggregatedIR = nextRPCStub.encryptedAggregateADD(rawIR);
            // LogUtils.debug(aggregatedIR.getValList());
            // 解密结果
            result = Converter.encryptedIR2Table(aggregatedIR, paillier);

        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
            // builder.setCode(false).setMessage(temp);
        } finally {
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void decryptAggregatedORDER(RPCCommon.AggregatedIR aggregatedIR, StreamObserver<RPCCommon.FinalResult> responseObserver) {
        RPCCommon.FinalResult.Builder builder = RPCCommon.FinalResult.newBuilder();
        try {
            List<String> keyList = aggregatedIR.getKeyList();
            List<String> valList = aggregatedIR.getValList();
            LogUtils.debug(keyList.size());
            LogUtils.debug(valList.size());
            for (int i = 0; i < keyList.size(); i++) {
                String key = keyList.get(i);

                String encryptedVal = valList.get(i);

                Long val = ope.decrypt(Long.valueOf(encryptedVal));

                builder.addKey(key);
                builder.addVal(val.toString());
            }

        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
        } finally {
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        }
    }

    private static OPE ope;

    static {
        ope = new OPE();
    }

    @Override
    public void aggregationORDER(RPCCommon.SQLExpression expression, StreamObserver<RPCCommon.RawIR> responseObserver) {
        String querySQL = SQLBuilder.buildORDERAggregationSQL(expression);
        // String querySQL = SQLBuilder.buildAggregationSQL(expression);
        RPCCommon.RawIR rawIR = null;
        try {
            ResultSet rs = executor.executeSql(querySQL);
            // 查询并加密
            rawIR = Converter.convertResultSet2RawIRORDER(rs, ope);
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
            // builder.setCode(false).setMessage(temp);
        } finally {
            responseObserver.onNext(rawIR);
            responseObserver.onCompleted();
        }
    }


    @Override
    public void pubicQuery(RPCCommon.SQLExpression sqlExpression, StreamObserver<RPCCommon.QueryResult> responseObserver) {
        ResultSet rs;
        RPCCommon.QueryResult.Builder resultBuilder = RPCCommon.QueryResult.newBuilder();
        try {
            System.out.println(sqlExpression);

            String querySQL = SQLBuilder.newBuilder().setSQLExpression(sqlExpression).buildPublicSQL();

            rs = executor.executeSql(querySQL);
            resultBuilder.setTable(ResultsUtils.resultSet2Table(rs));

            rs.close();
            LogUtils.info(String.format("result size: %d", resultBuilder.getTable().getRowCount()));
        } catch (Exception e) {
            String temp = LogUtils.buildErrorMessage(e);
            LogUtils.debug(temp);
            resultBuilder.setStatus(RPCCommon.Status.newBuilder().setCode(false).setMessage(temp).build());
        } finally {
            responseObserver.onNext(resultBuilder.setStatus(RPCCommon.Status.newBuilder().setCode(true).build()).build());
            responseObserver.onCompleted();
        }
    }
}
