package com.ada.federate;

import com.ada.federate.config.ClientConfig;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.utils.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class TestApplication {
    private static PRCClient queryInterface = null;
    public static LinkedHashMap<String, Long> timeframeList = new LinkedHashMap<>();
    private static RPCCommon.SQLExpression sqlExpression = null;
    private static String querySQL, sqlFileName, publicOrPrivate;
    private static Integer dataSiloNumber = 2;

    public static void main(String[] args) throws Exception {
        try {
            sqlFileName = args[0];
            publicOrPrivate = args[1];
            ThreadTools.delay = Long.parseLong(args[2]);

            System.out.printf("args: %s \n", Arrays.toString(args));

            // Boolean preAgg = Integer.parseInt(args[3]) == 1;

            System.out.printf("data silo number %d \n", dataSiloNumber);
            // TODO 初始化工作
            ClientConfig clientConfig = new ClientConfig("config.json");
            queryInterface = new PRCClient(clientConfig);
            querySQL = PathUtils.readSQL("sql", sqlFileName);
            LogUtils.debug(String.format("查询语句为：%s", querySQL));

            queryInterface.rpcHello();
            Thread.sleep(2000);

            if (publicOrPrivate.contains("public")) {
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                queryInterface.publicQuery(querySQL);
                endTime = System.currentTimeMillis();
                Tools.formatPrintTestInfo(startTime, endTime);
                // publicQueryResult.printResultTable(sqlExpression, 20);
            } else {
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                queryInterface.privateQuery(querySQL);
                endTime = System.currentTimeMillis();
                Tools.formatPrintTestInfo(startTime, endTime);
            }

            if (PRCClient.executorService != null)
                PRCClient.executorService.shutdown();

            System.exit(0);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }


    static {
        sqlFileName = "test_max.sql";
        // sqlFileName = "example.sql";
        // sqlFileName = "test_max.sql";
    }

    @Test
    public void testPublic() {
        try {
            long startTime, endTime;
            startTime = System.currentTimeMillis();
            RPCCommon.FinalResult resTable = queryInterface.publicQuery(querySQL);
            endTime = System.currentTimeMillis();
            Tools.formatPrintTestInfo(startTime, endTime);
            ResultsUtils.printFinalResult(resTable);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }

    @Test
    public void testPrivate() {
        try {
            long startTime, endTime;
            startTime = System.currentTimeMillis();
            RPCCommon.FinalResult rs = queryInterface.privateQuery(querySQL);
            endTime = System.currentTimeMillis();
            Tools.formatPrintTestInfo(startTime, endTime);
            ResultsUtils.printFinalResult(rs);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }

    @BeforeAll
    static void beforeFunction() {
        try {
            for (int i = 1; i <= dataSiloNumber; i++) {
                // TestUtils.winCMD(String.format("docker start vertical%d", i));
                // TestUtils.winCMD(String.format("docker exec vertical%d bash -c \"tc qdisc add dev eth0 root netem delay 2ms rate 100mbit\"", i));
                TestUtils.winCMD(String.format("docker exec vertical%d bash -c \"cd root/container && ./start-driver.sh \"", i));
            }

            Thread.sleep(2000);
            // TODO 初始化工作
            ClientConfig clientConfig = new ClientConfig("config.json");
            queryInterface = new PRCClient(clientConfig);
            querySQL = PathUtils.readSQL("sql", sqlFileName);
            // querySQL = "select gender, avg(age), loan, avg(deposit) from police inner join bank on bank.id = police.id group by gender, loan;";
            // sqlExpression = SQLUtils.parseSQL2SQLExpression(querySQL, targetSchema);
            // System.out.println(sqlExpression);
            queryInterface.rpcHello();
            Thread.sleep(3000);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }

    @AfterAll
    static void afterFunction() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                for (int i = 1; i <= dataSiloNumber; i++) {
                    TestUtils.winCMD(String.format("docker exec vertical%d bash -c \"cd root/container && ./stop-all.sh\"", i));
                }
            }
            if (PRCClient.executorService != null) PRCClient.executorService.shutdown();
            System.exit(0);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }
}
