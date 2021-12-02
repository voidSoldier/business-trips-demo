package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.config.RabbitConfig;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.domain.TripMsgWrapper;
import com.cmpn.tripsdemo.domain.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TripConsumer {

  private final TripService tripService;

  private static final Logger log = LoggerFactory.getLogger(TripConsumer.class);

  public TripConsumer(TripService tripService) {
    this.tripService = tripService;
  }

  @RabbitListener(queues = {RabbitConfig.TRIPS_QUEUE})
  public void receiveMessage(TripMsgWrapper msg) {
    log.info("Message received: {}", msg);
    String type = msg.getMsgType();
    Trip trip = msg.getTrip();

    switch (type) {
      case "save-upd":
        tripService.saveToDb(trip);
        break;
      case "delete":
        tripService.deleteFromDb(trip);
        break;
      default:
        log.error("Method with [{}] type doesn't exist", type);
    }
  }
}
