#!/bin/bash

echo "Testing Kafka notification system..."

# Test order notification
echo "Testing order notification..."
docker exec autowarehouse-kafka kafka-console-producer --topic notification-events --bootstrap-server localhost:9092 << EOF
{"eventType":"orderConfirmed","userId":1,"title":"Test Order","message":"Test order notification","notificationType":"ORDER_CONFIRMED","referenceId":123,"referenceType":"ORDER","timestamp":"$(date -Iseconds)"}
EOF

# Test customer service notification
echo "Testing customer service notification..."
docker exec autowarehouse-kafka kafka-console-producer --topic notification-events --bootstrap-server localhost:9092 << EOF
{"eventType":"customerService","userId":1,"title":"Test Message","message":"Test customer service notification","notificationType":"CUSTOMER_SERVICE_MESSAGE","referenceId":456,"referenceType":"TICKET","timestamp":"$(date -Iseconds)"}
EOF

echo "Test messages sent. Check the application logs and UI for notifications."
