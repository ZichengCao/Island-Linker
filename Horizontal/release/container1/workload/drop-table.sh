#!/bin/bash

table_name="lineitem"
schema_name="public"
database_name='postgres'

psql -d $database_name -c "DROP TABLE IF EXISTS $schema_name.$table_name;" >/dev/null

if [ $? -eq 0 ]; then
  echo "Table $schema_name.$table_name dropped successfully."
else
  echo "Error dropping table $schema_name.$table_name."
fi