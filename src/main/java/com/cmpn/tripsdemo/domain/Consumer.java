package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private TripMongoRepo tripRepo;

  private FeignClient client;

  private static final Logger log = LoggerFactory.getLogger(Consumer.class);

  public Consumer(TripMongoRepo tripRepo, FeignClient client) {
    this.tripRepo = tripRepo;
    this.client = client;
  }

  @RabbitListener(queues = {"trips-queue"})
  public void receiveMessage(TripMsgWrapper msg) {
    log.info("Message received: {}", msg);
    String type = msg.getMsgType();
    Trip trip = msg.getTrip();

    switch (type) {
      case "save-upd":
        saveOrUpdate(trip);
        break;
      case "delete":
        delete(trip);
        break;
        default: // exception to be added
        log.error("Method with [{}] type doesn't exist", type);
    }
  }

  private void saveOrUpdate(Trip trip) {
    tripRepo.save(enrich(trip));
  }

  private void delete(Trip trip) {
    tripRepo.delete(trip);
  }

  private Trip enrich(Trip trip) {
    // TODO: enriching

    return trip;
  }
}
