package com.astroviking.springjmsdemo.sender;

import com.astroviking.springjmsdemo.model.DemoMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.astroviking.springjmsdemo.config.JmsConfig.QUEUE_NAME;

@RequiredArgsConstructor
@Component
public class DemoSender {

  private final JmsTemplate jmsTemplate;

  @Scheduled(fixedRate = 2000)
  public void sendMessage() {
    System.out.println("Sending message");

    DemoMessage message =
        DemoMessage.builder().id(UUID.randomUUID()).message("Demo message").build();

    jmsTemplate.convertAndSend(QUEUE_NAME, message);

    System.out.println("Message sent");
  }
}
