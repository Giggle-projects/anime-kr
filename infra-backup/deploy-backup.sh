#!/bin/bash

# Define Container name
CONTAINER_NAME="anime-kr"
container_ids=$(docker ps -q --filter "publish=8080")

# container found with port 8080 exposed
if [ ! -z $container_ids ]; then
  # container found with name is anime-kr
  if [[ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]]; then
    # cleanup
    docker rm $CONTAINER_NAME
  fi
fi

# Check for any process using port 8080
PID_8080=$(lsof -i :8080 -t)

if [ ! -z "$PID_8080" ] ]; then
  for PID in PID_8080; do
    kill -9 PID
  done
fi

# build
docker run -d --name anime-kr -p 8080:8080 ghcr.io/giggle-projects/anime-kr:latest
