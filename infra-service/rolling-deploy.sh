#!/bin/bash

# Set the service name to restart
SERVICE_NAME="app1"

# Restart the specified service
docker-compose restart $SERVICE_NAME

# Perform a health check by waiting for the service to be healthy
HEALTH_CHECK_INTERVAL=10  # in seconds
MAX_RETRIES=30  # Maximum number of retries

retries=0

while [ $retries -lt $MAX_RETRIES ]; do
    HEALTH_STATUS=$(docker inspect --format='{{.State.Health.Status}}' "${SERVICE_NAME}")

    if [ "$HEALTH_STATUS" = "healthy" ]; then
        echo "Service $SERVICE_NAME is healthy!"
        exit 0
    else
        echo "Service $SERVICE_NAME is not healthy. Retrying in $HEALTH_CHECK_INTERVAL seconds..."
        sleep $HEALTH_CHECK_INTERVAL
        retries=$((retries + 1))
    fi
done

echo "Health check failed for service $SERVICE_NAME after $MAX_RETRIES retries."
exit 1