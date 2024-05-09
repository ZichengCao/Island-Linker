#!/bin/bash
set -e
path=$(pwd)

table_name="orders"
database_name='vertical'
schema_name="public"

for j in $(ls *.tbl); do
  table_name=$(echo $j | cut -d'.' -f1)
  echo "cutting $j"
  sed -i 's/|$//' *.tbl
  echo "importing data to $schema_name.$table_name"
  psql -d $database_name -c "SET search_path TO $schema_name; COPY $table_name FROM '$(pwd)/$j' DELIMITER '|' ENCODING 'LATIN1';"
done

if [ $? -eq 0 ]; then
  echo "Table $schema_name.$table_name imported successfully."
else
  echo "Error importing table $schema_name.$table_name."
fi
