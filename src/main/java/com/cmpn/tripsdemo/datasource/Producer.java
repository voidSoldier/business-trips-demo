package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.config.RabbitConfig;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.domain.TripMsgWrapper;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  private final RabbitTemplate rabbitTemplate;

  private static final Logger log = LoggerFactory.getLogger(Producer.class);

  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendWrappedMsg(String type, Trip trip) {
    TripMsgWrapper wrapper = new TripMsgWrapper(trip, type);
    rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE_NAME, Strings.EMPTY, wrapper);
    log.info("Message sent: {}", wrapper);
  }
}
