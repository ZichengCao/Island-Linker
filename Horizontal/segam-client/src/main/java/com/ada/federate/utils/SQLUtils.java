package com.ada.federate.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ada.federate.rpc.RPCCommon;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.*;

public class SQLUtils {
    public enum AGG_FUNCTION {SUM, AVG, COUNT, MAX, MIN, MEDIAN}

    public static List<String> getMetricsList(String sql) throws Exception {
        Select stmt = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect select = (PlainSelect) stmt.getSelectBody();
        List<String> dimensionList =
                select.getGroupBy().getGroupByExpressionList().getExpressions().stream().map(Object::toString).collect(Collectors.toList());
        List<String> metricList = new ArrayList<>(dimensionList);

        for (SelectItem selectItem : select.getSelectItems()) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    if (!dimensionList.contains(item.getExpression().toString())) {
                        if (item.getAlias() == null) {
                            metricList.add(item.getExpression().toString());
                        } else {
                            // metricList.add(item.getExpression().toString() + " as " + item.getAlias().getName());
                            metricList.add(item.getAlias().getName());
                        }
                    }
                }
            });
        }

        if (metricList.size() > dimensionList.size() + 1) {
            throw new Exception("More than 1 number field of aggregation function is not supported.");
        } else if (metricList.size() == dimensionList.size()) {
            metricList.add("");
        }
        return metricList;
    }

    public static RPCCommon.SQLExpression generateSQLExpression(String aggregation, List<String> metrics, List<String> dimensions, String targetSchema, String tableName) {
        RPCCommon.SQLExpression.Builder builder = RPCCommon.SQLExpression.newBuilder();
        builder.setUuid(System.currentTimeMillis())
                .setAggField(String.format("%s(%s)", aggregation, metrics.get(0)))
                .addAllDimensions(dimensions)
                .setTargetSchema(targetSchema)
                .setTableName(tableName);
        return builder.build();
    }

    public static RPCCommon.SQLExpression parseSQL2SQLExpression(String sql, String targetSchema) throws Exception {
        RPCCommon.SQLExpression.Builder builder = RPCCommon.SQLExpression.newBuilder();
        builder.setUuid(System.currentTimeMillis());
        Select stmt = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect select = (PlainSelect) stmt.getSelectBody();

        List<String> metricList = new ArrayList<>();
        List<String> dimensionList = new ArrayList<>();
        List<String> orderList = new ArrayList<>();
        for (SelectItem selectItem : select.getSelectItems()) {
            selectItem.accept(new SelectItemVisitorAdapter() {
                @Override
                public void visit(SelectExpressionItem item) {
                    if (item.getAlias() == null) {
                        metricList.add(item.getExpression().toString());
                    } else {
                        metricList.add(item.getExpression().toString() + " as " + item.getAlias().getName());
                    }
                }
            });
        }

        Expression where = select.getWhere();
        GroupByElement groupByElement = select.getGroupBy();
        Limit limit = select.getLimit();
        Offset offset = select.getOffset();
        if (groupByElement != null)
            dimensionList.addAll(select.getGroupBy().getGroupByExpressionList().getExpressions().stream().map(Object::toString).collect(Collectors.toList()));
        List<String> aliasList = new ArrayList<>(Collections.nCopies(dimensionList.size(), "null"));
        if (where != null) builder.setFilters(select.getWhere().toString());
        // dimensionList = select.getGroupBy().getGroupByExpressionList().getExpressions().stream().map(Object::toString).collect(Collectors.toList());
        if (select.getOrderByElements() != null) {
            orderList.addAll(select.getOrderByElements().stream().map(OrderByElement::toString).collect(Collectors.toList()));
            // order is not supported.
        }
        if (limit != null) builder.setLimit(Integer.parseInt(select.getLimit().getRowCount().toString()));
        if (offset != null) builder.setOffset(Integer.parseInt(select.getOffset().getOffset().toString()));

        if (metricList.size() > dimensionList.size() + 1) {
            throw new Exception("More than 1 number field of aggregation function is not supported.");
        }

        for (int i = 0; i < orderList.size(); i++) {
            String order = orderList.get(i);
            for (String metric : metricList) {
                if (metric.contains(" as ") && metric.contains(order.split(" ")[0])) {
                    String originName = metric.split(" as ")[0];
                    String aliasName = metric.split(" as ")[1];
                    orderList.set(i, orderList.get(i).replaceAll(aliasName, originName));
                }
            }
        }

        for (int i = 0; i < dimensionList.size(); i++) {
            String dimension = dimensionList.get(i);
            boolean flag = metricList.remove(dimension);
            if (flag) continue;
            for (int j = 0; j < metricList.size(); j++) {
                String metric = metricList.get(j);
                if (metric.contains(" as ") && metric.contains(dimension)) {
                    String originName = metric.split(" as ")[0];
                    String aliasName = metric.split(" as ")[1];
                    metricList.remove(metric);
                    j--;
                    dimensionList.set(i, originName);
                    aliasList.set(i, aliasName);
                }
            }
        }
        builder.setAggField(metricList.get(0))
                .addAllDimensions(dimensionList)
                .addAllAlias(aliasList)
                .addAllOrder(orderList)
                .setTargetSchema(targetSchema)
                .setTableName(select.getFromItem().toString());
        return builder.build();
    }

    public static RPCCommon.SQLExpression avg2Sum(RPCCommon.SQLExpression sqlExpression) {
        String originName = sqlExpression.getAggField();
        long originUUid = sqlExpression.getUuid();
        return sqlExpression.toBuilder().setUuid(originUUid + 1).setAggField(originName.toLowerCase().replaceAll("avg", "sum")).build();
    }

    public static RPCCommon.SQLExpression avg2Count(RPCCommon.SQLExpression sqlExpression) {
        String originName = sqlExpression.getAggField();
        long originUUid = sqlExpression.getUuid();
        return sqlExpression.toBuilder().setUuid(originUUid + 2).setAggField(originName.toLowerCase().replaceAll("avg", "count")).build();
    }

    public static AGG_FUNCTION parseQueryType(RPCCommon.SQLExpression expression) {
        String aggField = expression.getAggField();
        // QueryType queryType = null;
        for (AGG_FUNCTION type : AGG_FUNCTION.values()) {
            if (isEqual(type, aggField))
                return type;
        }
        return null;
    }

    public static boolean isEqual(AGG_FUNCTION AGGFUNCTION, String str) {
        return str.toLowerCase().contains(AGGFUNCTION.toString().toLowerCase());
    }
}
