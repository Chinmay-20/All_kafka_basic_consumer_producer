package com.chinmay.service;

import com.chinmay.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.CompletableFuture;
@Service
public class kafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message)
    { // we will send message from controller
        CompletableFuture<SendResult<String, Object>> future = template.send("chinmay-partition-control-1", 3, null, message);

        future.whenComplete((result,ex)->{
            if (ex==null){
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else{
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
        template.send("chinmay-partition-control-1", 3, null, "hi");
        template.send("chinmay-partition-control-1", 1, null, "hello");
        template.send("chinmay-partition-control-1", 2, null, "welcome");
        template.send("chinmay-partition-control-1", 2, null, "chinmay");
        template.send("chinmay-partition-control-1", 0, null, "message");




    }

    public void sendEventsToTopic(Customer customer)
    { // we will send message from controller
        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("chinmay-error-handling-1", customer);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + customer +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]"); // we will see this message when we run the tests
                } else {
                    System.out.println("Unable to send message=[" +
                            customer + "] due to : " + ex.getMessage());
                }
            });
        }catch (Exception ex)
        {
            System.out.println("ERROR : " + ex.getMessage());
        }

    }

}
