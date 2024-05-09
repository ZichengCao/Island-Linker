#!/bin/bash

set -e

# Function to release containers
deploy_containers() {
    for i in {1..2}; do
        docker run -it --name container$i -e POSTGRES_PASSWORD='123465' -v $(pwd)/release/container$i:/root/container -p 899$i:9991 -d postgres:13
        sleep 2
        docker exec container$i bash -c "cd root/container && chmod +x ./init.sh && ./init.sh"
        sleep 2
        docker exec -u postgres container$i bash -c "cd root/container/workload && ./drop-table.sh"
        docker exec -u postgres container$i bash -c "cd root/container/workload && ./create-table.sh"
        sleep 2
        docker exec -u postgres container$i bash -c "cd root/container/workload && ./import-table.sh"
    done
}

# Call deploy_containers function to deploy containers
deploy_containers
