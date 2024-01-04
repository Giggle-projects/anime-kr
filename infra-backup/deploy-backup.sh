#!/bin/bash

# Define Container name
CONTAINER_NAME="anime-kr"
PORT=8080
if [[ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]]; then
  docker rm -f $CONTAINER_NAME
fi

for PID in $(lsof -i :$PORT -t); do
  kill -9 $PID
done

docker run -d --name anime-kr -p 8080:8080 ghcr.io/giggle-projects/anime-kr:latest