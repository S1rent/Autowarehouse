#!/bin/bash

echo "Stopping Kafka services..."
docker-compose -f docker-compose.kafka.yml down

echo "Kafka services stopped."
