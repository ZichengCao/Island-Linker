package com.ada.federate.sql;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLBuilder {
    private final List<String> dimensionList = new ArrayList<>();
    private String aggField;
    private String schemaName, tableName, filter, dir;
    private List<String> order = new ArrayList<>();
    private Integer limit, offset;

    private SQLBuilder() {
    }

    public static SQLBuilder builder() {
        return new SQLBuilder();
    }

    public String buildSql() {
        String query = "select " + aggField;

        if (dimensionList.size() != 0) {
            // if (dbName.equals("Postgresql") || dbName.equals("MySQL"))
            query += ", concat(" + String.join(",'_',", dimensionList) + ") as dimension";
            // else if (dbName.equals("clickhouse")) {
            //     if (dimensionList.size() == 1)
            //         query += ", " + dimensionList.get(0) +
            //                 " dimension";
            //     else
            //         query += ", concat(" + dimensionList.stream().map(x -> "toString(" + x + ")").collect(Collectors.joining(",'_',")) + ") as" +
            //                 " dimension";
            // }
        }

        query += " from " + schemaName + "." + tableName;
        if (!StringUtils.isEmpty(filter))
            query += " where " + filter;
        if (dimensionList.size() != 0)
            query += " group by " + String.join(",", dimensionList);
        if (order != null && !order.isEmpty()) {
            query += " order by " + String.join(",", order);
            if (dir != null && !dir.isEmpty())
                query += " " + dir;
        }
        // select count(id),concat(city,' ',silo_id) from test_schema.example_table group by city,silo_id;
        if (limit != null && limit != 0) {
            query += " limit " + limit;
            if (offset != null && offset != 0)
                query += " offset " + offset;
        }
        query += ";";
        return query;
    }

    public String buildPlainQuerySql() {
        String query = "select ";
        Pattern pattern = Pattern.compile("(?i)median\\s*\\((\\s*[a-z_]+\\s*)\\)");
        Matcher matcher = pattern.matcher(aggField);
        if (matcher.find()) {
            String medianExp = matcher.group(1);
            query += medianExp;
        }
        if (dimensionList.size() != 0) {
            query += ", concat(" + String.join(",'_',", dimensionList) + ") as dimension";
        }
        query += " from " + schemaName + "." + tableName;
        if (!StringUtils.isEmpty(filter))
            query += " where " + filter;
        return query;
    }

    public String buildMedianSql() {
        String query = "select ";

        if (dimensionList.size() != 0) {
            // if (dbName.equals("Postgresql") || dbName.equals("MySQL"))
            query += "concat(" + String.join(",'_',", dimensionList) + ") as dimension, ";
        }
        Pattern pattern = Pattern.compile("(?i)median\\s*\\((\\s*[a-z_]+\\s*)\\)");
        Matcher matcher = pattern.matcher(aggField);
        if (matcher.find()) {
            String medianExp = matcher.group(1);
            query += String.format("percentile_disc(0.5) within group (order by %s) as median_%s", medianExp, medianExp);
        }
        query += " from " + schemaName + "." + tableName;
        if (!StringUtils.isEmpty(filter))
            query += " where " + filter;
        if (dimensionList.size() != 0)
            query += " group by " + String.join(",", dimensionList);
        return query;
    }

    public SQLBuilder setDataSource(String targetSchema, String tableName) {
        this.schemaName = targetSchema;
        this.tableName = tableName;
        return this;
    }


    public SQLBuilder setAggField(String aggField) {
        this.aggField = aggField;
        return this;
    }

    public SQLBuilder setFilters(String filter) {
        this.filter = (filter);
        return this;
    }

    public SQLBuilder setDimensions(List<String> dimensions) {
        if (dimensions != null && !dimensions.isEmpty()) {
            dimensionList.addAll(dimensions);
        }
        return this;
    }

    public SQLBuilder setLimitAndOrder(List<String> order, String dir, Integer limit, Integer offset) {
        this.order = order;
        this.dir = dir;
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public List<String> getDimensionList() {
        return dimensionList;
    }

    public String getAggField() {
        return aggField;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
