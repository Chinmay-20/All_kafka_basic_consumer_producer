package com.chinmay;

import com.chinmay.dto.Customer;
import com.chinmay.service.kafkaMessagePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers // we annotate to tell this class is using some containers
class KafkaProducerExampleApplicationTests {

	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")); // get specific version for me

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry){
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

	}

	@Autowired
	private kafkaMessagePublisher publisher;

	@Test
	public void testSendEventsToTopic() {
		publisher.sendEventsToTopic(new Customer(123, "chinmay", "chinmay@gmail.com", "1234567"));
		await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted(() -> { // assert statement});
		});

	}
}
