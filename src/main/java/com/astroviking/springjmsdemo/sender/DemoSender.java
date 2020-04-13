package com.astroviking.springjmsdemo.sender;

import com.astroviking.springjmsdemo.model.DemoMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

import static com.astroviking.springjmsdemo.config.JmsConfig.QUEUE_NAME;
import static com.astroviking.springjmsdemo.config.JmsConfig.SEND_RECEIVE_QUEUE;

@RequiredArgsConstructor
@Component
public class DemoSender {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedRate = 2000)
  public void sendMessage() {
    System.out.println("Sending message");

    DemoMessage message =
        DemoMessage.builder().id(UUID.randomUUID()).message("Demo message").build();

    jmsTemplate.convertAndSend(QUEUE_NAME, message);

    System.out.println("Message sent");
  }

  @Scheduled(fixedRate = 2000)
  public void sendAndReceiveMessage() throws JMSException {

    DemoMessage message =
        DemoMessage.builder().id(UUID.randomUUID()).message("Demo message").build();


    Message receivedMessage = jmsTemplate.sendAndReceive(
        SEND_RECEIVE_QUEUE,
            session -> {
              try {
                Message demoMessage =
                    session.createTextMessage(objectMapper.writeValueAsString(message));
                demoMessage.setStringProperty("_type", "com.astroviking.springjmsdemo.model");
                System.out.println("Sending message");
                return demoMessage;
              } catch (JsonProcessingException e) {
                throw new JMSException(e.getMessage());
              }
            });

    System.out.println("Message received: " +receivedMessage.getBody(String.class) );
  }
}
