package com.example.jwt_demo1.Tempalte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.websocket.SendResult;

@Component
public class KafkaSenderExample {
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    KafkaSenderExample(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    void sendMessage(String message, String topicname){
        kafkaTemplate.send(topicname, message);
    }


}
