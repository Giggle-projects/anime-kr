#!/bin/bash

# Perform a health check by waiting for the service to be healthy
HEALTH_CHECK_INTERVAL=1  # interval seconds
MAX_RETRIES=10            # Maximum number of retries

# Set the service name to restart
SERVICE_NAME="anime-1"

# Get health check endpoint of service
URL=localhost
PORT=$(docker port $SERVICE_NAME | cut -d: -f2)
HEALTH_CHECK_ENDPOINT=${URL}:${PORT}/actuator/health

# Restart the specified service
echo "Restart $SERVICE_NAME"
docker-compose restart $SERVICE_NAME
sleep $HEALTH_CHECK_INTERVAL

retries=0
while [ $retries -lt $MAX_RETRIES ]; do
    echo "Health check with $HEALTH_CHECK_ENDPOINT ..."
    is_container_running=$(docker inspect --format='{{.State.Running}}' "${SERVICE_NAME}")
    api_health=$(curl -s ${HEALTH_CHECK_ENDPOINT} | jq -r '.status | tostring')
    if [ "${is_container_running}" == "true" ] && [ "${api_health}" == "UP" ]; then
        echo "$SERVICE_NAME is healthy!"
        break
    else
        echo "$SERVICE_NAME is not healthy. Retrying in $HEALTH_CHECK_INTERVAL seconds..."
        sleep $HEALTH_CHECK_INTERVAL
        retries=$((retries + 1))
    fi
done

if [ ${retries} -ge ${MAX_RETRIES} ]; then
    echo "Health check failed for service $SERVICE_NAME after $MAX_RETRIES retries."
    echo "ERROR :: Deploy failed : $SERVICE_NAME"
    exit 1
fi
