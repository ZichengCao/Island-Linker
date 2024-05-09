package com.ada.federate;

import com.ada.federate.utils.LogUtils;

import static com.ada.federate.utils.LogUtils.buildErrorMessage;

public class ClientApplication {

    private static String querySQL = "select count(salary) from test_schema.example_table group by city;";

    private static String configPath = "config.json";

    public static void main(String[] args) {
        try {
            // ClientConfig clientConfig = new ClientConfig(configPath);
            // RPCCommon.SQLExpression sqlExpression = SQLUtils.parseSQL2SQLExpression(querySQL);
            // FederateDBInterface queryInterface = new FederateDBInterface(clientConfig);
            // queryInterface.publicQuery(sqlExpression);
        } catch (Exception e) {
            LogUtils.error(buildErrorMessage(e));
        }
    }
}
