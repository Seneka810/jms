package com.kord.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kord.jms.config.JmsConfig;
import com.kord.jms.model.SimpleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){

        SimpleMessage simpleMessage = SimpleMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello world")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.QUEUE_NAME, simpleMessage);

    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        SimpleMessage simpleMessage = SimpleMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.QUEUE_SEND_RCV_NAME, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMsg = null;
                try {
                    helloMsg = session.createTextMessage(objectMapper.writeValueAsString(simpleMessage));
                    helloMsg.setStringProperty("_type", "com.kord.jms.model.SimpleMessage");

                    System.out.println("Sending hello");

                    return helloMsg;
                } catch (JsonProcessingException e) {
                    throw new JMSException("exception");
                }
            }
        });

        System.out.println(receivedMsg.getBody(String.class));

    }
}
