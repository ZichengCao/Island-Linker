package com.ada.federate.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ada.federate.config.ClientConfig;
import com.ada.federate.rpc.RPCCommon;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

public class SQLUtils {


    static Pattern pattern = Pattern.compile("(\\w+)\\((\\w+)\\)");

    private static final Map<String, RPCCommon.FuncType> funcTypeMap = new HashMap<>();

    static {
        funcTypeMap.put("sum", RPCCommon.FuncType.SUM);
        funcTypeMap.put("count", RPCCommon.FuncType.COUNT);
        funcTypeMap.put("max", RPCCommon.FuncType.MAX);
        funcTypeMap.put("min", RPCCommon.FuncType.MIN);
        funcTypeMap.put("avg", RPCCommon.FuncType.AVG);
        funcTypeMap.put("median", RPCCommon.FuncType.MEDIAN);
        // 添加更多映射
    }

    public static RPCCommon.FuncType translate2Enum(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            String func = matcher.group(1).strip();
            return funcTypeMap.get(func.toLowerCase());
        }
        return RPCCommon.FuncType.SUM;
    }

    public static RPCCommon.SQLExpression parseSQL2SQLExpression(String sql, ClientConfig.Endpoint endpoint) throws Exception {
        return parseSQL2SQLExpression(sql, endpoint, System.currentTimeMillis());
    }

    public static RPCCommon.SQLExpression parseSQL2SQLExpression(String sql, ClientConfig.Endpoint endpoint, long uuid) throws Exception {
        PlainSelect select = (PlainSelect) ((Select) CCJSqlParserUtil.parse(sql)).getSelectBody();

        RPCCommon.SQLExpression.Builder builder = RPCCommon.SQLExpression.newBuilder();

        String aggregations = null, dimensions = null;

        RPCCommon.FuncType funcType = RPCCommon.FuncType.NONE;

        String joinField = null;


        String targetSchema = endpoint != null ? endpoint.getSchema() : "", targetTable = endpoint != null ? endpoint.getTable().getName() : "";

        dimensions = select.getGroupBy() == null ? null : select.getGroupBy().getGroupByExpressionList().getExpressions().stream().map(Object::toString).filter(x -> (endpoint == null || endpoint.getTable().belongs(x))).findFirst() // 获取第一个匹配的元素
                .orElse(null); // 如果没有匹配的元素则返回 null

        if (dimensions == null) {
            // avg(age)
            aggregations = select.getSelectItems().stream().map(Object::toString).filter(x -> (endpoint == null || endpoint.getTable().belongs(x))).findFirst() // 获取第一个匹配的元素
                    .orElse(null); // 如果没有匹配的元素则返回 null
            funcType = SQLUtils.translate2Enum(aggregations);
            Matcher match = pattern.matcher(aggregations);
            match.find();
            aggregations = match.group(2);
        }


        // FromItem from = select.getFromItem();
        Join join = select.getJoins().get(0);
        // 获取连接条件表达式
        Expression onExpression = join.getOnExpressions().iterator().next();

        if (endpoint != null && onExpression instanceof EqualsTo) {
            EqualsTo equalsTo = (EqualsTo) onExpression;

            Column leftColumn = (Column) equalsTo.getLeftExpression();
            String leftTableName = leftColumn.getTable().getName();
            String leftColumnName = leftColumn.getColumnName();


            Column rightColumn = (Column) equalsTo.getRightExpression();
            String rightTableName = rightColumn.getTable().getName();
            String rightColumnName = rightColumn.getColumnName();

            joinField = endpoint.getTable().getName().equals(leftTableName) ? leftColumnName : rightColumnName;
        }

        builder.setUuid(uuid);

        if (aggregations != null) builder.setAggregation(aggregations);
        if (funcType != null) builder.setFuncType(funcType);
        if (dimensions != null) builder.setDimension(dimensions);
        if (targetSchema != null) builder.setTargetSchema(targetSchema);
        if (targetTable != null) builder.setTableName(targetTable);
        if (joinField != null) builder.setJoinField(joinField);

        builder.setHashFlag(sql.toLowerCase().contains("max") || sql.toLowerCase().contains("min") || sql.toLowerCase().contains("median"));
        return builder.build();
    }


    public static void main(String[] args) throws Exception {
        String sql = "SELECT c_nationkey, MAX(o_totalprice) FROM orders\n" +
                "INNER JOIN customer ON customer.c_custkey = orders.o_custkey\n" +
                "GROUP BY c_nationkey;";

        ClientConfig clientConfig = new ClientConfig("config-backup.json");
        RPCCommon.SQLExpression expression1 = parseSQL2SQLExpression(sql, clientConfig.getEndpoints().get(0));
        RPCCommon.SQLExpression expression2 = parseSQL2SQLExpression(sql, clientConfig.getEndpoints().get(1));
        System.out.println(expression1);
        System.out.println(expression2);
    }
}
