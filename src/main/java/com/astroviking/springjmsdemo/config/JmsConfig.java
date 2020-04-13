package com.astroviking.springjmsdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

  public static final String QUEUE_NAME = "demo_queue";
  public static final String SEND_RECEIVE_QUEUE = "demo_queue_send_receive";

  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");

    return converter;
  }
}
