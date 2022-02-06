package com.kord.jms.listener;

import com.kord.jms.config.JmsConfig;
import com.kord.jms.model.SimpleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.QUEUE_NAME)
    public void listen(@Payload SimpleMessage simpleMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
//        System.out.println("I got a message");

//        System.out.println(simpleMessage);
    }

    @JmsListener(destination = JmsConfig.QUEUE_SEND_RCV_NAME)
    public void listenForHello(@Payload SimpleMessage simpleMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException {

        SimpleMessage payloadMsg = SimpleMessage
                .builder()
                .id(UUID.randomUUID())
                .message("world")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }
}
