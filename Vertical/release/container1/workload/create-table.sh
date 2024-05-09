#!/bin/bash
set -e

table_name="orders"
database_name='vertical'
schema_name="public"

if [ $(psql -lqt | cut -d \| -f 1 | grep -w $database_name | wc -l) -eq 0 ]; then
    createdb $database_name
fi

psql $database_name -c "CREATE SCHEMA IF NOT EXISTS $schema_name;"
psql $database_name -c "
    CREATE TABLE $schema_name.ORDERS (
    O_ORDERKEY bigint NOT NULL PRIMARY KEY,
    O_CUSTKEY int NOT NULL,
    O_ORDERSTATUS text NOT NULL,
    O_TOTALPRICE DECIMAL(15, 2) NOT NULL,
    O_ORDERDATE date NOT NULL,
    O_ORDERPRIORITY text NOT NULL,
    O_CLERK text NOT NULL,
    O_SHIPPRIORITY int NOT NULL,
    O_COMMENT text NOT NULL
);"

if [ $? -eq 0 ]; then
    echo "Table $schema_name.$table_name created successfully."
else
    echo "Error creating table $schema_name.$table_name."
fi
