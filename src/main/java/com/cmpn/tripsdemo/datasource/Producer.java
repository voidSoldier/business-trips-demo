package com.cmpn.tripsdemo.datasource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String exchange, String key, String msg) {
    rabbitTemplate.convertAndSend(exchange, key, msg);
  }

}
