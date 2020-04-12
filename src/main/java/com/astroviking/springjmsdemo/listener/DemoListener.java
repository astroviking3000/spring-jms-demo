package com.astroviking.springjmsdemo.listener;

import com.astroviking.springjmsdemo.config.JmsConfig;
import com.astroviking.springjmsdemo.model.DemoMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
@RequiredArgsConstructor
public class DemoListener {

  @JmsListener(destination = JmsConfig.QUEUE_NAME)
  public void listen(
      @Payload DemoMessage demoMessage, @Headers MessageHeaders headers, Message message) {
    System.out.println("Message received");
    System.out.println(demoMessage.getId());
    System.out.println(demoMessage.getMessage());
  }
}
