package com.astroviking.springjmsdemo.listener;

import com.astroviking.springjmsdemo.config.JmsConfig;
import com.astroviking.springjmsdemo.model.DemoMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DemoListener {

  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.QUEUE_NAME)
  public void listen(
      @Payload DemoMessage demoMessage, @Headers MessageHeaders headers, Message message) {
    System.out.println("Message received");
    System.out.println(demoMessage.getId());
    System.out.println(demoMessage.getMessage());
  }

  @JmsListener(destination = JmsConfig.SEND_RECEIVE_QUEUE)
  public void listenForDemo(Message message) throws JMSException {

    DemoMessage demoResponse =
        DemoMessage.builder().id(UUID.randomUUID()).message("Demo response").build();

    jmsTemplate.convertAndSend(message.getJMSReplyTo(), demoResponse);
  }
}
