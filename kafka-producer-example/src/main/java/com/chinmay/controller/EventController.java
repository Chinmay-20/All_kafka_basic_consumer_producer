package com.chinmay.controller;


import com.chinmay.dto.Customer;
import com.chinmay.service.kafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    @Autowired
    private kafkaMessagePublisher publisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        try {
            for (int i = 0 ; i<100;i++) {
                publisher.sendMessageToTopic(message + " " + i);
            }
            return ResponseEntity.ok("message published successfully...");
        } catch(Exception ex){ // if my kafka server is down I will get this error

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/publish")
    public void sendEvents(@RequestBody Customer customer){

        publisher.sendEventsToTopic(customer);
    }
}

// from postman we will send customer object in form of JSON and then we are giving that to kafka template , kafka template will publish that object to topic
//