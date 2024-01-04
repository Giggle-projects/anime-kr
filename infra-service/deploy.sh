#!/bin/bash

# Define service names
SERVICE_NAMES=("anime-1" "anime-2" "anime-3")

# Perform a health check by waiting for the service to be healthy
INITIAL_SLEEP=20
HEALTH_CHECK_INTERVAL=3   # interval seconds
MAX_RETRIES=10            # Maximum number of retries

for SERVICE_NAME in "${SERVICE_NAMES[@]}"; do
  echo "=== INFO :: Update $SERVICE_NAME ==="

  # Get health check endpoint of service
  URL=localhost
  PORT=$(docker port $SERVICE_NAME | cut -d: -f2)
  HEALTH_CHECK_ENDPOINT=${URL}:${PORT}/actuator/health

  is_init_container_running=$(docker inspect --format='{{.State.Running}}' "${SERVICE_NAME}")
  if [ "${is_init_container_running}" == "true" ]; then
    # Turn on the service if service is not running from beginning
    docker-compose up $SERVICE_NAME -d
    sleep INITIAL_SLEEP
  else
    # Restart the specified service
    docker-compose restart $SERVICE_NAME
    sleep INITIAL_SLEEP

  # Check container status, API health with retry strategy
  retries=0
  while [ $retries -lt $MAX_RETRIES ]; do
      echo "Health check with $HEALTH_CHECK_ENDPOINT ..."
      is_container_running=$(docker inspect --format='{{.State.Running}}' "${SERVICE_NAME}")
      api_health=$(curl -s "${HEALTH_CHECK_ENDPOINT}" | jq -r '.status')
      if [ "${is_container_running}" == "true" ] && [ "${api_health}" == "UP" ]; then
          echo "Service $SERVICE_NAME is healthy!"
          break
      else
          sleep $HEALTH_CHECK_INTERVAL
          retries=$((retries + 1))
      fi
  done

  # Throw exception when health check failed
  if [ $retries -ge $MAX_RETRIES ]; then
      echo "Health check failed for service $SERVICE_NAME after $MAX_RETRIES retries."
      echo "=== ERROR :: Deploy failed : $SERVICE_NAME ==="
      exit 1
  fi

  echo ""
done