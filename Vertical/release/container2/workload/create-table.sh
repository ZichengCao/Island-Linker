#!/bin/bash
set -e

table_name="lineitem"
database_name='vertical'
schema_name="public"

if [ $(psql -lqt | cut -d \| -f 1 | grep -w $database_name | wc -l) -eq 0 ]; then
    createdb $database_name
fi

psql $database_name -c "CREATE SCHEMA IF NOT EXISTS $schema_name;"
psql $database_name -c "
    CREATE TABLE $schema_name.lineitem (
        L_ORDERKEY INTEGER NOT NULL,
        L_PARTKEY INTEGER NOT NULL,
        L_SUPPKEY INTEGER NOT NULL,
        L_LINENUMBER INTEGER NOT NULL,
        L_QUANTITY DECIMAL(15,2) NOT NULL,
        L_EXTENDEDPRICE DECIMAL(15,2) NOT NULL,
        L_DISCOUNT DECIMAL(15,2) NOT NULL,
        L_TAX DECIMAL(15,2) NOT NULL,
        L_RETURNFLAG CHAR(1) NOT NULL,
        L_LINESTATUS CHAR(1) NOT NULL,
        L_SHIPDATE DATE NOT NULL,
        L_COMMITDATE DATE NOT NULL,
        L_RECEIPTDATE DATE NOT NULL,
        L_SHIPINSTRUCT CHAR(25) NOT NULL,
        L_SHIPMODE CHAR(10) NOT NULL,
        L_COMMENT VARCHAR(44) NOT NULL
    );"

if [ $? -eq 0 ]; then
    echo "Table $schema_name.$table_name created successfully."
else
    echo "Error creating table $schema_name.$table_name."
fi
