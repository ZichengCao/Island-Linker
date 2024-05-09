#!/bin/bash

set -e

# Function to package the program
package_program() {
    mvn clean package
    cp -f segam-client/target/segam-client-0.1-jar-with-dependencies.jar release/core.jar

    source_file="segam-server/target/postgresql-driver-jar-with-dependencies.jar"
    for i in {1..3}; do
        cp "$source_file" "release/container$i/server.jar"
    done
}

# Call package_program function to package the program
package_program



