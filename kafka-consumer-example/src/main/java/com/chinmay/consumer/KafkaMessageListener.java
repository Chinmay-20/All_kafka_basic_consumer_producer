package com.chinmay.consumer;

import com.chinmay.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

public class KafkaMessageListener {

    Logger logger =  LoggerFactory.getLogger(KafkaMessageListener.class);

    @RetryableTopic(attempts = "4", backoff = @Backoff (delay = 3000,multiplier = 1.5, maxDelay = 15000), exclude = {NullPointerException.class, RuntimeException.class}) // execute 3 times create 3 retry topic
    // Backoff is if you don't want to do retry immediately
    @KafkaListener(topics = "chinmay-error-handling-1", groupId = "ck-group-error-handling")
    public void consumeEvents(Customer customer){
        try {
            logger.info("consumer consume the events {}", customer.toString());
            List<String> restrcitedContact = Stream.of("+15859570527").collect(Collectors.toList());
            if (restrcitedContact.contains(customer.getContactNo())) {
                throw new RuntimeException("Invalid contact number received");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @DltHandler
    public void listenDLT(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset){
        logger.info("DLT received : {}, from {}, offset {}", customer.getName(), topic, offset );
    }

//    @KafkaListener(topics = "chinmay-partition-control-1", groupId = "ck-group-4", topicPartitions = {@TopicPartition(topic = "chinmay-partition-control-1", partitions={"2"})})
//    public void consumeEvents(String customer){
//
//        logger.info("consumer1 consume the events {}" ,customer.toString());
//
//    }


//    @KafkaListener(topics = "chinmay-demo6", groupId = "ck-group-2")
//    public void consume2(String message){
//
//        logger.info("consumer2 consume the message {}" ,message);
//
//    }
//
//    @KafkaListener(topics = "chinmay-demo6", groupId = "ck-group-2")
//    public void consume3(String message){
//
//        logger.info("consumer3 consume the message {}" ,message);
//
//    }
//
//    @KafkaListener(topics = "chinmay-demo6", groupId = "ck-group-2")
//    public void consume4(String message){
//
//        logger.info("consumer4 consume the message {}" ,message);
//
//    }

}

// faced exception if source is a trusted and is it safe to deserialize

