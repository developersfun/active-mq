package com.tdf.activeamqp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component @Slf4j
@RequiredArgsConstructor
@EnableJms
public class ActiveMQConsumer {

    @JmsListener(destination = "QUEUE_TOPIC")
    public void receiveMessage(Message message) {
        try{
            log.info("Received message from activeMQ : {}", message);

            //TODO : Do your thing here
        } catch (Exception e) {
            log.error("Unknown Error occurred in processing CustomMessage", e);
            throw new RuntimeException("Problem in receiving message from Active MQ");
        }
    }

}
