package com.ada.federate;

import com.ada.federate.cache.ResultKVSet;
import com.ada.federate.pojo.ClientConfig;
import com.ada.federate.rpc.RPCCommon;
import com.ada.federate.utils.LogUtils;
import com.ada.federate.utils.PathUtils;
import com.ada.federate.utils.SQLUtils;
import com.ada.federate.utils.StopWatch;
import org.apache.commons.cli.*;


public class MainApplication {

    private static String sqlFileName, queryMode;

    private static Integer maxPrintLine = 10;

    private static String targetSchema = "public";

    private static void CommandLineInfo(String[] args) {
        Options options = new Options();
        options.addOption("i", true, "Required, input SQL file name, e.g., test_max.sql.");
        options.addOption("p", true, "Optional, specify whether the query is plaintext or ciphertext, e.g., public, private. Default is private.");
        options.addOption("l", true, "Optional, set the maximum output rows of the query result, defaulting to 10.");
        options.addOption("h", false, "Display help information.");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar main.jar [-i name]", options);
                System.exit(0); // Exit the program
            }

            if (!cmd.hasOption("i")) {
                System.err.println("Missing required option: -i");
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar main.jar [-i name]", options);
                System.exit(1); // Exit with error code 1
            }

            sqlFileName = cmd.getOptionValue("i");
            queryMode = cmd.getOptionValue("p", "private");
            maxPrintLine = Integer.parseInt(cmd.getOptionValue("l", "10"));

        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
            System.exit(1); // Exit with error code 1
        }
    }

    public static void main(String[] args) throws Exception {
        try {

            CommandLineInfo(args);

            ClientConfig clientConfig = new ClientConfig("config.json");

            Integer dataSiloNumber = clientConfig.getEndpoints().size();

            System.out.println(String.format("data owner number %d\n", dataSiloNumber));

            PRCClient queryInterface = new PRCClient(clientConfig);

            String querySQL = PathUtils.readSQL("sql", sqlFileName);

            RPCCommon.SQLExpression sqlExpression = SQLUtils.parseSQL2SQLExpression(querySQL, targetSchema);

            queryInterface.rpcHello(sqlExpression);

            Thread.sleep(2000);

            if (queryMode.contains("public")) {
                long startTime, endTime;
                // TODO public query
                startTime = System.currentTimeMillis();
                ResultKVSet publicQueryResult = queryInterface.publicQuery(sqlExpression);
                endTime = System.currentTimeMillis();
                publicQueryResult.printResultTable(sqlExpression, maxPrintLine);
                StopWatch.formatPrintTestInfo(startTime, endTime);
            } else {
                long startTime, endTime;
                // TODO private query
                startTime = System.currentTimeMillis();
                ResultKVSet privateQueryResult = queryInterface.privateQuery(sqlExpression,
                        SQLUtils.parseQueryType(sqlExpression));
                endTime = System.currentTimeMillis();
                privateQueryResult.printResultTable(sqlExpression, maxPrintLine);
                StopWatch.formatPrintTestInfo(startTime, endTime);
            }
            queryInterface.cleanBuffer(sqlExpression.getUuid());

            if (PRCClient.executorService != null)
                PRCClient.executorService.shutdown();
            System.exit(0);
        } catch (Exception e) {
            LogUtils.error(LogUtils.buildErrorMessage(e));
        }
    }
}
