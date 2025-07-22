@echo off
echo Setting up Kafka for Autowarehouse...

REM Start Kafka services
echo Starting Kafka services...
docker-compose -f docker-compose.kafka.yml up -d

REM Wait for Kafka to be ready
echo Waiting for Kafka to be ready...
timeout /t 30 /nobreak

REM Create topics
echo Creating Kafka topics...
docker exec autowarehouse-kafka kafka-topics --create --topic notification-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec autowarehouse-kafka kafka-topics --create --topic order-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec autowarehouse-kafka kafka-topics --create --topic customer-service-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

REM List topics to verify
echo Verifying topics...
docker exec autowarehouse-kafka kafka-topics --list --bootstrap-server localhost:9092

echo Kafka setup complete!
echo Kafka UI available at: http://localhost:8080
pause
