package com.ada.federate.sql;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.utils.LogUtils;

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


    // public ResultSet executeSql(RPCCommon.SQLExpression expression) throws SQLException {
    //
    //     SQLBuilder builder = SQLBuilder.newBuilder()
    //             .setAggregations(expression.getAggregationsList())
    //             .setDataSource(expression.getTargetSchema(), expression.getTableName())
    //             .setDimensions(expression.getDimensionsList())
    //             .setFields(expression.getFieldsList());
    //
    //     return executeSql(builder.buildSql());
    // }
}
