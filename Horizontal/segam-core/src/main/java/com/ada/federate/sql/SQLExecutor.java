package com.ada.federate.sql;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.ResultsUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLExecutor {

    private final Connection conn;
    private final Statement stmt;

    public SQLExecutor(Connection conn) throws SQLException {
        this.conn = conn;
        stmt = this.conn.createStatement();
    }

    public ResultSet executeSql(String sql) throws SQLException {
        LogUtils.info("sql in executing: " + sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }


    public ResultSet executeSql(RPCCommon.SQLExpression expression) throws SQLException {
        SQLBuilder builder = SQLBuilder.builder()
                .setAggField(expression.getAggField())
                .setDataSource(expression.getTargetSchema(), expression.getTableName())
                .setDimensions(expression.getDimensionsList())
                .setFilters(expression.getFilters())
                .setLimitAndOrder(expression.getOrderList(), expression.getDir(), expression.getLimit(), expression.getOffset());

        if (builder.getAggField().toLowerCase().contains("median")) {
            return executeSql(builder.buildPlainQuerySql());
        } else {
            return executeSql(builder.buildSql());
        }
    }
}
