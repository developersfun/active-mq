package com.tdf.activeamqp.controller;

import com.tdf.activeamqp.CustomMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

@Component @Slf4j
@RequiredArgsConstructor
public class ActiveMQProducer {

    @Qualifier("jmsTemplate")
    @NonNull private final JmsOperations jmsTemplate;

    public void produceMessage(String messageUUID,
                               CustomMessage customMessage,
                               final long delay) {

        try{
            log.info("Sending Message with JMSCorrelationID : {}", messageUUID);

            jmsTemplate.convertAndSend("QUEUE_TOPIC", customMessage, msgProcessor -> {
                msgProcessor.setJMSCorrelationID(messageUUID);
                msgProcessor.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                msgProcessor.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 0);
                return msgProcessor;
            });


        } catch (Exception e) {
            log.error("Caught the exception!!! ", e);
            throw new RuntimeException("Cannot send message to the Queue");
        }
    }

}
