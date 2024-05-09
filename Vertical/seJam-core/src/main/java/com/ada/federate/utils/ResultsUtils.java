package com.ada.federate.utils;

import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.rpc.RPCCommon.Cell;
import com.ada.federate.rpc.RPCCommon.ElementType;
import com.ada.federate.rpc.RPCCommon.Row;
import com.ada.federate.rpc.RPCCommon.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.ada.federate.pojo.TableProxy.cell;
import static com.ada.federate.pojo.TableProxy.row;

public class ResultsUtils {

    public static String getCurrentTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义东八区的时区偏移量
        int zoneOffset = 8;
        // 构造时区对象
        var zoneId = "GMT+" + zoneOffset;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // 格式化当前时间为 "HH:mm:ss" 的字符串
        return now.atZone(java.time.ZoneId.of(zoneId))
                .format(formatter);
    }

    public static void printFinalResult(RPCCommon.FinalResult finalResult) {
        List<String> keys = finalResult.getKeyList();
        List<String> values = finalResult.getValList();

        System.out.println("Final Result:");
        System.out.println("------------");
        int count = 10;
        for (int i = 0; i < keys.size() && count > 0; i++) {
            System.out.println(keys.get(i) + ", " + values.get(i));
            count--;
        }
    }

    public static Table resultSet2Table(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData(); // 获取键名
        int columnCount = md.getColumnCount(); // 获取列的数量
        boolean firstLineFlag = true;
        Table.Builder tableBuilder = Table.newBuilder();
        while (rs.next()) {
            Row.Builder rowBuilder = Row.newBuilder();
            if (firstLineFlag) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnType = md.getColumnTypeName(i).toLowerCase();
                    System.out.println(columnType);
                    if (columnType.contains("int")) {
                        tableBuilder.addType(ElementType.INTEGER);
                    } else if (columnType.contains("numeric")) {
                        // ..
                    } else {
                        tableBuilder.addType(ElementType.STRING);
                    }

                    tableBuilder.addName(md.getColumnName(i));
                }
                firstLineFlag = false;
            }

            for (int i = 1; i <= columnCount; i++) {
                // 获取当前行的数据
                Object value = rs.getObject(i);
                String valStr = value.toString();
                rowBuilder.addCell(Cell.newBuilder().setVal(valStr).build());
                // if (value.getClass() == String.class) {
                //     rowBuilder.addCell(Cell.newBuilder().setVal(valStr).build());
                // } else {
                //     int decimalIndex = valStr.indexOf(".");
                //     String integerPart = (decimalIndex != -1) ? valStr.substring(0, decimalIndex) : valStr;
                //     Integer intVal = Integer.parseInt(integerPart);
                //     rowBuilder.addCell(Cell.newBuilder().setVal(intVal.toString()).build());
                // }
            }
            tableBuilder.addRow(rowBuilder.build());
        }
        return tableBuilder.build();
    }

    public static void printTable(Table table) {
        printTable(table, table.getRowCount());
    }

    public static void printTable(Table table, int lines) {
        for (String name : table.getNameList()) {
            System.out.printf("%-20s", name);
        }

        int times = table.getNameCount();
        StringBuilder separatorLine = new StringBuilder();
        // 宽度为 20，与下面一列的宽度保持一致
        while (times-- != 0) separatorLine.append("--------------------");
        System.out.println();
        System.out.println(separatorLine);
        for (Row row : table.getRowList()) {
            if (lines-- <= 0) break;
            for (int i = 0; i < row.getCellCount(); i++) {
                Cell cell = row.getCell(i);
                if (table.getType(i) == ElementType.INTEGER) {
                    System.out.printf("%-20d", Integer.valueOf(cell.getVal()));
                } else if (table.getType(i) == ElementType.STRING) {
                    System.out.printf("%-20s", cell.getVal());
                } else if (table.getType(i) == ElementType.FLOAT) {
                    System.out.printf("%-20.2f", Float.valueOf(cell.getVal()));
                }
            }
            System.out.println();
        }
        System.out.println(separatorLine);
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData(); // 获取键名
        int columnCount = md.getColumnCount(); // 获取行的数量
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-25s", md.getColumnName(i));
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-25s", rs.getObject(i));
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        // 创建第一个 Table
        Table table1 = Table.newBuilder()
                .addAllType(Arrays.asList(ElementType.INTEGER, ElementType.INTEGER, ElementType.INTEGER))
                .addRow(row(cell(1), cell(15), cell(20)))
                .addRow(row(cell(2), cell(10), cell(25)))
                .addRow(row(cell(3), cell(25), cell(30)))
                .addRow(row(cell(4), cell(20), cell(35)))
                .addAllName(Arrays.asList("id", "deposit", "loan"))
                .build();

        printTable(table1);
    }


}
