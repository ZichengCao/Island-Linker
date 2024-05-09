#!/bin/bash

set -e

# Function to package the program
package_program() {
    mvn clean package
    cp -f seJam-client/target/SeJam-Client-jar-with-dependencies.jar release/core.jar

    source_file="seJam-server/target/SeJam-Server-jar-with-dependencies.jar"
    for i in {1..2}; do
        cp "$source_file" "release/container$i/server.jar"
    done
}

# Call package_program function to package the program
package_program
