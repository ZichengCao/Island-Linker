package com.ada.federate.pojo;

import com.ada.federate.rpc.RPCCommon.*;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.ResultsUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableProxy {

    private Table table;

    public TableProxy(Table table) {
        this.table = table;
    }

    public Column getColumn(String name) {
        Column.Builder builder = Column.newBuilder();
        int columnIndex = getAttributeIndex(name);
        if (columnIndex == -1) return null;
        ElementType type = table.getType(columnIndex);
        builder.setType(type).setName(name);
        if (type == ElementType.INTEGER) {
            for (Row row : table.getRowList()) {
                builder.addIntVal(Integer.parseInt(row.getCell(columnIndex).getVal()));
            }
        } else if (type == ElementType.FLOAT) {
            for (Row row : table.getRowList()) {
                builder.addFloatVal(Float.parseFloat(row.getCell(columnIndex).getVal()));
            }
        } else {
            for (Row row : table.getRowList()) {
                builder.addStrVal(row.getCell(columnIndex).getVal());
            }
        }
        return builder.build();
    }


    public TableProxy sortBy(String name) {
        int columnIndex = getAttributeIndex(name);
        if (columnIndex == -1) return this;

        int finalColumnIndex = columnIndex;
        List<Row> rowList = new ArrayList<>(table.getRowList());

        // boolean numericFlag = table.getType(getAttributeIndex(name)) != ElementType.STRING;

        Collections.sort(rowList, new Comparator<Row>() {
            @Override
            public int compare(Row row1, Row row2) {
                // Assuming columnIndex is valid
                Cell cell1 = row1.getCellList().get(finalColumnIndex);
                Cell cell2 = row2.getCellList().get(finalColumnIndex);
                return cell1.getVal().compareTo(cell2.getVal());
            }
        });

        table = table.toBuilder().clearRow().addAllRow(rowList).build();
        return this;
    }


    public static Table mergeTables(Table left, String leftJoin, Table right, String rightJoin) {
        Table.Builder builder = Table.newBuilder();
        int joinFieldIndex = -1;
        for (int i = 0; i < right.getNameCount(); i++) {
            // hard code
            if (right.getName(i).equalsIgnoreCase(rightJoin)) {
                joinFieldIndex = i;
                break;
            }
        }


        int finalJoinFieldIndex = joinFieldIndex;

        builder.addAllType(left.getTypeList()).addAllType(IntStream.range(0, right.getTypeCount()).filter(i -> i != finalJoinFieldIndex).mapToObj(right::getType).collect(Collectors.toList()));
        builder.addAllName(left.getNameList()).addAllName(IntStream.range(0, right.getNameCount()).filter(i -> i != finalJoinFieldIndex).mapToObj(right::getName).collect(Collectors.toList()));

        Map<String, Row> leftTableMap = table2Map(left);
        Map<String, List<Row>> rightTableMap = table2MapList(right);
        // LogUtils.debug(leftTableMap);
        // LogUtils.debug(rightTableMap);
        for (String id : leftTableMap.keySet()) {
            if (rightTableMap.containsKey(id)) {
                Row r1 = leftTableMap.get(id);
                List<Row> r2s = rightTableMap.get(id);
                for (Row r2 : r2s) {
                    builder.addRow(r1.toBuilder().addAllCell(IntStream.range(0, r2.getCellCount()).filter(i -> i != finalJoinFieldIndex).mapToObj(r2::getCell).collect(Collectors.toList())).build());
                }
            }
        }

        return builder.build();
    }


    private Integer getIntVal(Row row, String name) {
        return Integer.parseInt(getVal(row, name));
    }

    public Table collect() {
        return this.table;
    }


    public TableProxy groupBy(List<FuncType> funcTypeList, List<String> aggregateAttributeList, List<String> groupingAttributeList) {
        Table.Builder resultTableBuilder = Table.newBuilder().addAllName(groupingAttributeList).addAllType(IntStream.range(0, groupingAttributeList.size()).mapToObj(x -> ElementType.STRING).collect(Collectors.toList()));

        // key: group attribute, val: index
        Map<String, List<Integer>> groupByMap = new HashMap<>();

        List<Row.Builder> rowList = new ArrayList<>();
        Map<String, Integer> keyIndexMap = new HashMap<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = table.getRow(i);
            String key = groupingAttributeList.stream().map(a -> getVal(row, a)).collect(Collectors.joining(","));
            if (!groupByMap.containsKey(key)) {
                groupByMap.put(key, new ArrayList<>());
                keyIndexMap.put(key, rowList.size());
                rowList.add(Row.newBuilder().addAllCell(groupingAttributeList.stream().map(a -> cell(getVal(row, a))).collect(Collectors.toList())));
                // resultTableBuilder.addRow(row(groupingAttributeList.stream().map(a -> cell(getVal(row, a))).collect(Collectors.toList())));
            }
            groupByMap.get(key).add(i);
        }

        for (int i = 0; i < funcTypeList.size(); i++) {
            FuncType func = funcTypeList.get(i);
            String aggregateAttribute = aggregateAttributeList.get(i);

            resultTableBuilder.addName(func.toString() + "(" + aggregateAttribute + ")").addType(ElementType.FLOAT);

            for (String key : groupByMap.keySet()) {
                Integer keyIndex = keyIndexMap.get(key);
                if (func == FuncType.SUM) {
                    int sum = groupByMap.get(key).stream().map(x -> getIntVal(table.getRow(x), aggregateAttribute)).mapToInt(Integer::intValue).sum();
                    rowList.get(keyIndex).addCell(cell(sum));
                    // rowList.get(keyIndex).addCell(cell(sum));
                } else if (func == FuncType.COUNT) {
                    int count = groupByMap.get(key).size();
                    rowList.get(keyIndex).addCell(cell(count));
                } else if (func == FuncType.AVG) {
                    Double val = groupByMap.get(key).stream().map(x -> getIntVal(table.getRow(x), aggregateAttribute)).mapToInt(Integer::intValue).average().getAsDouble();
                    rowList.get(keyIndex).addCell(cell(val));
                } else if (func == FuncType.MAX) {
                    int maxVal = groupByMap.get(key).stream().map(x -> getIntVal(table.getRow(x), aggregateAttribute)).max(Integer::compareTo).get();
                    rowList.get(keyIndex).addCell(cell(maxVal));
                } else if (func == FuncType.MIN) {
                    int minVal = groupByMap.get(key).stream().map(x -> getIntVal(table.getRow(x), aggregateAttribute)).min(Integer::compareTo).get();
                    rowList.get(keyIndex).addCell(cell(minVal));
                } else if (func == FuncType.MEDIAN) {
                    List<Integer> tempList = groupByMap.get(key).stream().map(x -> getIntVal(table.getRow(x), aggregateAttribute)).collect(Collectors.toList());
                    tempList.sort(Integer::compareTo);
                    // 10 -->  0,1,2,3,4,5,6,7,8,9 --> size / 2, (size / 2) -1
                    // 9 --> 0,1,2,3,4,5,6,7,8 --> floor(size / 2)
                    int ans = (tempList.size() & 1) == 1 ? tempList.get(tempList.size() / 2) : (tempList.get(tempList.size() / 2) + tempList.get(tempList.size() / 2 - 1)) / 2;
                    rowList.get(keyIndex).addCell(cell(ans));
                }
            }
        }
        table = resultTableBuilder.addAllRow(rowList.stream().map(Row.Builder::build).collect(Collectors.toList())).build();
        return this;
    }


    private String getVal(Row row, String name) {
        int columnIndex = getAttributeIndex(name);
        if (columnIndex == -1) {
            return null;
        }
        return row.getCell(columnIndex).getVal();
    }


    public static void main(String[] args) {
        // 创建第一个 Table
        // Table table1 = Table.newBuilder().addAllType(Arrays.asList(ElementType.INTEGER, ElementType.INTEGER, ElementType.INTEGER)).addRow(row(cell(1), cell(15), cell(20))).addRow(row(cell(2), cell(10), cell(25))).addRow(row(cell(3), cell(25), cell(30))).addRow(row(cell(4), cell(20), cell(35))).addAllName(Arrays.asList("id", "deposit", "loan")).build();
        //
        // // 创建第二个 Table
        // Table table2 = Table.newBuilder().addAllType(Arrays.asList(ElementType.INTEGER, ElementType.INTEGER, ElementType.INTEGER)).addRow(row(cell(2), cell(25), cell("male"))).addRow(row(cell(1), cell(25), cell("male"))).addRow(row(cell(4), cell(27), cell("male"))).addRow(row(cell(3), cell(31), cell("female"))).addAllName(Arrays.asList("id", "age", "gender")).build();
        // Table joinTable = mergeTables(table1, table2);
        // // 之后你可以按需使用 table1 和 table2，例如打印它们：
        // TableProxy proxy = new TableProxy(joinTable);
        // Table ans = proxy.groupBy(Collections.singletonList(FuncType.MEDIAN), Collections.singletonList("deposit"), Collections.singletonList("gender")).table;
        // ResultsUtils.printTable(ans);
        // printTable(table2);
    }

    public int getAttributeIndex(String name) {
        int columnIndex = -1;
        for (int i = 0; i < table.getNameList().size(); i++) {
            if (table.getName(i).equals(name)) {
                columnIndex = i;
                break;
            }
        }
        return columnIndex;
    }

    public static Map<String, Row> table2Map(Table table) {
        Map<String, Row> map = new HashMap<>();
        for (Row row : table.getRowList()) {
            map.put(row.getCell(0).getVal(), row);
        }
        return map;
    }

    public static Map<String, List<Row>> table2MapList(Table table) {
        Map<String, List<Row>> map = new HashMap<>();
        for (Row row : table.getRowList()) {
            String key = row.getCell(0).getVal();
            if (!map.containsKey(key))
                map.put(key, new ArrayList<>());
            map.get(key).add(row);
        }
        return map;
    }


    public static Cell cell(Object val) {
        return Cell.newBuilder().setVal(val.toString()).build();
    }


    public static Row row(List<Cell> cells) {
        Row.Builder builder = Row.newBuilder();
        for (Cell cell : cells) {
            builder.addCell(cell);
        }
        return builder.build();
    }

    public static Row row(Cell... cells) {
        Row.Builder builder = Row.newBuilder();
        for (Cell cell : cells) {
            builder.addCell(cell);
        }
        return builder.build();
    }
}
