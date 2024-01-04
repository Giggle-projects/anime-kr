#!/bin/bash

# Define Container name
CONTAINER_NAME="anime-kr"
IS_CONTAINER_PORT=docker ps -q --filter "publish=8080"
IS_SAME_NAME_CONTAINER=$(docker ps --format '{{.Names}}' | grep 'anime-kr')

# container found with port 8080 exposed
if [ [ -z "$IS_CONTAINER_PORT" ] ]; then
  # No container found with name is  anime-kr
  if [ ! [ docker ps --format '{{.Names}}' | grep '^anime-kr$' ] ]; then
    # cleanup
    docker rm $CONTAINER_NAME
  fi
fi

# Check for any process using port 8080
PID_8080=$(lsof -i :8080 -t)

if [ ! [ -z "$pids" ] ]; then
  for PID in PID_8080; do
    kill -9 PID
  done
fi

# build
docker run -d --name anime-kr -p 8080:8080 ghcr.io/giggle-projects/anime-kr:latest
