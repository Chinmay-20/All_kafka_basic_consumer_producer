package com.chinmay;

import com.chinmay.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
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
@Testcontainers
@Slf4j
class KafkaConsumerExampleApplicationTests {

	// consumer of producer needs to test their application that whether they are able to successfully face the events from that specific topic or not
	// that is the reason we write test case
	@Container
	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")); // get specific version for me

	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry){
		registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

	}

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	// forcefully send some topic then in consumer you need to write logic and validate whether it is consuming or not

	@Test
	public void testConsumeEvents() {
		log.info("testConsumeEvents method execution started...");
		Customer customer = new Customer(123, "chinmay", "chinmay@gmail.com", "1234567");
		kafkaTemplate.send("chinmay-demo-test-2", "customer");
		log.info("testConsumeEvents method execution started...");
		await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted(() -> { // assert statement});
		});
	}

}
