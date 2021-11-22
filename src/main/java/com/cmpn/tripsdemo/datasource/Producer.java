package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.config.RabbitConfig;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.domain.TripMsgWrapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendWrappedMsg(String type, Trip trip) {
    TripMsgWrapper wrapper = new TripMsgWrapper(trip, type);
    rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE_NAME, Strings.EMPTY, wrapper);
    System.out.println("________________ message sent");
  }
}
