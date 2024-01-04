#!/bin/bash

# Define Container anme
CONTAINER_NAME="anime-kr"

# Setting variable about sleep time & max retries value
INITIAL_SLEEP=20
MAX_RETRIES=10

if [ ! "$(docker ps -a -q -f name=$CONTAINER_NAME)" ]; then
  if [ "$(docker ps -aq -f status=exited -f name=$CONTAINER_NAME)" ]; then
  # cleanup
  docker rm $CONTAINER_NAME
  fi
  # run your container
  retries=0
  while [ $retries -lt $MAX_RETRIES ]; do
    if [ ! "$(docker ps -a -q -f name=anime-kr)" ]; then
      docker run -d --name anime-kr -p 8080:8080 ghcr.io/giggle-projects/anime-kr:latest
      retries=$((retries + 1))
      sleep $INITIAL_SLEEP
    else
      break
    fi
  done
fi
