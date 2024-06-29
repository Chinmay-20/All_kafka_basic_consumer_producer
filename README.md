# Kafka Consumer and Producer Example

This repository contains a simple demonstration of Kafka's consumer and producer capabilities using Spring Boot.

## System Requirements

- Java 17
- Maven
- Kafka Broker (Local or Remote)

## Setup and Configuration

1. **Clone the Repository**:

2. **Start Kafka Broker**:
Ensure your Kafka broker is running on `localhost:9092` or modify the `application.yml` for both consumer and producer to point to your Kafka broker.

3. **Build Projects**:
Navigate into each project directory (`consumer`, `producer`) and run.

## Producer

The producer sends messages to a Kafka topic.

### Configuration

- **Key Serializer**: `org.apache.kafka.common.serialization.StringSerializer`
- **Value Serializer**: `org.springframework.kafka.support.serializer.JsonSerializer`
- **Server Port**: 9191 (modifiable in `application.yml`)

### Topics

Producer sends messages to various topics, which can be configured in the Kafka admin panel or using a script.

### Running the Producer

Navigate to the producer directory and run.

## Consumer

The consumer reads messages from the Kafka topics.

### Configuration

- **Key Deserializer**: `org.apache.kafka.common.serialization.StringDeserializer`
- **Value Deserializer**: `org.springframework.kafka.support.serializer.JsonDeserializer`
- **Group ID**: `ck-group-4`
- **Trusted Packages**: `com.chinmay.dto`, `java.util`, `java.lang`
- **Server Port**: 9292 (modifiable in `application.yml`)

### Running the Consumer

Navigate to the consumer directory and run.

## Testing

Both components include test configurations using `spring-kafka-test` and `testcontainers` to simulate Kafka behavior for testing.

## Contributions

Contributions are welcome. Please fork the repository and submit a pull request.

## License

This project is open source and available under the [MIT License](LICENSE.md).
