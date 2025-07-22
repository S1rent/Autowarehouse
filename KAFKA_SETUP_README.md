# Kafka Setup for Autowarehouse Notification System

## Overview
This document provides instructions for setting up Apache Kafka locally to support the notification system in the Autowarehouse application.

## Prerequisites
- Java 8 or higher
- Docker and Docker Compose (recommended approach)
- OR Manual Kafka installation

## Option 1: Docker Compose Setup (Recommended)

### 1. Create docker-compose.yml for Kafka
Create a `docker-compose.kafka.yml` file in the project root:

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    hostname: zookeeper
    container_name: autowarehouse-zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    volumes:
      - zookeeper-data:/var/lib/zookeeper/data
      - zookeeper-logs:/var/lib/zookeeper/log

  kafka:
    image: confluentinc/cp-kafka:7.4.0
    hostname: kafka
    container_name: autowarehouse-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9101:9101"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_JMX_PORT: 9101
      KAFKA_JMX_HOSTNAME: localhost
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
    volumes:
      - kafka-data:/var/lib/kafka/data

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: autowarehouse-kafka-ui
    depends_on:
      - kafka
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:29092
      KAFKA_CLUSTERS_0_ZOOKEEPER: zookeeper:2181

volumes:
  zookeeper-data:
  zookeeper-logs:
  kafka-data:
```

### 2. Start Kafka Services
```bash
# Start Kafka and Zookeeper
docker-compose -f docker-compose.kafka.yml up -d

# Check if services are running
docker-compose -f docker-compose.kafka.yml ps

# View logs
docker-compose -f docker-compose.kafka.yml logs -f kafka
```

### 3. Create Required Topics
```bash
# Access Kafka container
docker exec -it autowarehouse-kafka bash

# Create topics
kafka-topics --create --topic notification-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
kafka-topics --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
kafka-topics --create --topic customer-service-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List topics to verify
kafka-topics --list --bootstrap-server localhost:9092

# Exit container
exit
```

### 4. Access Kafka UI
- Open browser and go to `http://localhost:8080`
- You can monitor topics, messages, and consumer groups

## Option 2: Manual Kafka Installation

### 1. Download Kafka
```bash
# Download Kafka
wget https://downloads.apache.org/kafka/2.13-3.6.0/kafka_2.13-3.6.0.tgz
tar -xzf kafka_2.13-3.6.0.tgz
cd kafka_2.13-3.6.0
```

### 2. Start Zookeeper
```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties
```

### 3. Start Kafka Server
```bash
# In a new terminal, start Kafka
bin/kafka-server-start.sh config/server.properties
```

### 4. Create Topics
```bash
# Create notification-events topic
bin/kafka-topics.sh --create --topic notification-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# Create order-events topic
bin/kafka-topics.sh --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# Create customer-service-events topic
bin/kafka-topics.sh --create --topic customer-service-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

# List topics
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

## Kafka Topics Used in Autowarehouse

### 1. notification-events
- **Purpose**: General notification events for users
- **Producers**: NotificationProducer service
- **Consumers**: NotificationConsumer service
- **Message Types**: NotificationEvent DTO

### 2. order-events
- **Purpose**: Order-related events (status changes, creation)
- **Producers**: OrderService, NotificationProducer
- **Consumers**: NotificationConsumer service
- **Message Types**: OrderEvent DTO

### 3. customer-service-events
- **Purpose**: Customer service chat events
- **Producers**: ChatService, NotificationProducer
- **Consumers**: NotificationConsumer service
- **Message Types**: CustomerServiceEvent DTO

## Notification Event Types

### Customer Notifications:
- `orderShipped` - When admin marks order as shipped
- `orderDelivered` - When admin marks order as delivered
- `orderCancelled` - When admin cancels order
- `orderRefunded` - When admin processes refund
- `customerService` - When admin sends customer service message

### Admin Notifications:
- `orderConfirmed` - When customer places new order
- `adminCustomerService` - When customer sends message to support

## Testing Kafka Setup

### 1. Test Producer
```bash
# Send test message to notification-events topic
docker exec -it autowarehouse-kafka bash
kafka-console-producer --topic notification-events --bootstrap-server localhost:9092
# Type some test messages and press Enter
```

### 2. Test Consumer
```bash
# In another terminal, consume messages
docker exec -it autowarehouse-kafka bash
kafka-console-consumer --topic notification-events --from-beginning --bootstrap-server localhost:9092
```

### 3. Monitor Consumer Groups
```bash
# Check consumer groups
kafka-consumer-groups --bootstrap-server localhost:9092 --list

# Check specific consumer group details
kafka-consumer-groups --bootstrap-server localhost:9092 --group notification-consumer-group --describe
```

## Application Configuration

The application is already configured with the following Kafka settings in `application.properties`:

```properties
# Kafka Configuration
kafka.bootstrap.servers=localhost:9092

# Producer configurations
mp.messaging.outgoing.notification-events.connector=smallrye-kafka
mp.messaging.outgoing.notification-events.topic=notification-events
mp.messaging.outgoing.order-events.connector=smallrye-kafka
mp.messaging.outgoing.order-events.topic=order-events
mp.messaging.outgoing.customer-service-events.connector=smallrye-kafka
mp.messaging.outgoing.customer-service-events.topic=customer-service-events

# Consumer configurations
mp.messaging.incoming.notification-events-consumer.connector=smallrye-kafka
mp.messaging.incoming.notification-events-consumer.topic=notification-events
mp.messaging.incoming.notification-events-consumer.group.id=notification-consumer-group
# ... (other consumer configs)
```

## Troubleshooting

### Common Issues:

1. **Connection Refused**
   - Ensure Kafka is running on localhost:9092
   - Check if Docker containers are up: `docker ps`

2. **Topic Not Found**
   - Create topics manually using the commands above
   - Enable auto-topic creation in Kafka config

3. **Consumer Group Issues**
   - Reset consumer group offset if needed:
     ```bash
     kafka-consumer-groups --bootstrap-server localhost:9092 --group notification-consumer-group --reset-offsets --to-earliest --topic notification-events --execute
     ```

4. **Memory Issues**
   - Increase Docker memory allocation
   - Adjust Kafka heap size in docker-compose.yml

### Useful Commands:

```bash
# Stop Kafka services
docker-compose -f docker-compose.kafka.yml down

# Stop and remove volumes (clean slate)
docker-compose -f docker-compose.kafka.yml down -v

# View Kafka logs
docker logs autowarehouse-kafka -f

# Access Kafka container
docker exec -it autowarehouse-kafka bash
```

## Integration with Application

1. **Start Kafka** using Docker Compose
2. **Start Backend** with `mvn quarkus:dev`
3. **Test Notifications** through the API endpoints
4. **Monitor** via Kafka UI at http://localhost:8080

The notification system will automatically:
- Produce events when orders are updated or messages are sent
- Consume events to create database notifications
- Send real-time notifications via WebSocket
- Handle retry logic for failed message processing

## Next Steps

After Kafka is running:
1. Test order status updates to trigger notifications
2. Test customer service messages
3. Verify notifications appear in the frontend
4. Monitor Kafka UI for message flow
5. Check application logs for any issues
