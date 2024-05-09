package com.ada.federate.sql;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class SQLBuilder {
    private String dimension;
    private String aggregation;

    private RPCCommon.FuncType funcTypeList;
    private String schemaName, tableName, joinField;


    private SQLBuilder() {
    }

    public static SQLBuilder newBuilder() {
        return new SQLBuilder();
    }


    public String buildPublicSQL() {
        // 开始构建 SQL 语句
        StringBuilder sqlBuilder = new StringBuilder("SELECT ");

        List<String> tempList = new ArrayList<>();

        tempList.add(joinField);

        if (aggregation != null)
            tempList.add(String.format("CAST(%s AS INTEGER)", aggregation));
        if (dimension != null)
            tempList.add(dimension);

        String metrics = String.join(",", tempList);
        sqlBuilder.append(metrics);

        sqlBuilder.append(" FROM ").append(schemaName).append(".").append(tableName);

        // 返回构建好的 SQL 语句
        return sqlBuilder.toString();
    }

    public static String buildGroupSQL(RPCCommon.SQLExpression expression) {
        return String.format("SELECT %s, STRING_AGG(%s::TEXT, '_') AS ids FROM " +
                "%s.%s GROUP BY %s;", expression.getDimension(), expression.getJoinField(), expression.getTargetSchema(), expression.getTableName(), expression.getDimension());
    }


    public static String buildAggregationSQL(RPCCommon.SQLExpression expression) {
        return String.format("select CAST(%s AS INTEGER), %s from %s.%s;",
                expression.getAggregation(), expression.getJoinField(),
                expression.getTargetSchema(),
                expression.getTableName()
        );
    }

    public static String buildADDAggregationSQL(RPCCommon.SQLExpression expression) {
        return String.format("select SUM(CAST(%s AS INTEGER)), %s from %s.%s GROUP BY %s;",
                expression.getAggregation(), expression.getJoinField(),
                expression.getTargetSchema(),
                expression.getTableName(),
                expression.getJoinField()
        );
    }

    public static String buildORDERAggregationSQL(RPCCommon.SQLExpression expression) {
        LogUtils.debug(expression);
        if (expression.getFuncType() == RPCCommon.FuncType.MEDIAN) {
            return String.format("select CAST(%s AS INTEGER), %s from %s.%s;",
                    expression.getAggregation(), expression.getJoinField(),
                    expression.getTargetSchema(),
                    expression.getTableName()
            );
        } else {
            return String.format("select MAX(CAST(%s AS INTEGER)), %s from %s.%s GROUP BY %s;",
                    expression.getAggregation(), expression.getJoinField(),
                    expression.getTargetSchema(),
                    expression.getTableName(),
                    expression.getJoinField()
            );
        }
    }

    public SQLBuilder setSQLExpression(RPCCommon.SQLExpression expression) {
        this.setAggregations(expression.getAggregation())
                .setDataSource(expression.getTargetSchema(), expression.getTableName())
                .setDimensions(expression.getDimension())
                .setFuncTypes(expression.getFuncType())
                .setJoinField(expression.getJoinField());
        return this;
    }

    public SQLBuilder setDataSource(String targetSchema, String tableName) {
        this.schemaName = targetSchema;
        this.tableName = tableName;
        return this;
    }


    public SQLBuilder setAggregations(String aggregation) {
        if (aggregation != null && !aggregation.isEmpty()) {
            this.aggregation = aggregation;

        }
        return this;
    }

    public SQLBuilder setDimensions(String dimension) {
        if (dimension != null && !dimension.isEmpty()) {
            this.dimension = dimension;
        }
        return this;
    }


    public SQLBuilder setFuncTypes(RPCCommon.FuncType funcType) {
        if (funcType != null) {
            this.funcTypeList = funcType;
        }
        return this;
    }

    public SQLBuilder setJoinField(String joinField) {
        this.joinField = joinField;
        return this;
    }

    public String getDimension() {
        return dimension;
    }

    public String getAggregation() {
        return aggregation;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public SQLBuilder setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public SQLBuilder setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public static String QUERY_ID_SQL = "SELECT %s FROM %s";


}
