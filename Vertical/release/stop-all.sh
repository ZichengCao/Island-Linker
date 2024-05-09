for i in {1..2}; do
    docker exec container$i bash -c "cd root/container && ./stop.sh"
done
