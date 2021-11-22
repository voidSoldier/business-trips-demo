package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private TripMongoRepo tripRepo;

  private FeignClient client;

  public Consumer(TripMongoRepo tripRepo, FeignClient client) {
    this.tripRepo = tripRepo;
    this.client = client;
  }

  @RabbitListener(queues = {"trips-queue"})
  public void receiveMessage(TripMsgWrapper msg) {
    System.out.println("received in 'getMessage': " + msg);
    String type = msg.getMsgType();
    Trip trip = msg.getTrip();

    switch (type) {
      case "save-upd":
        saveOrUpdate(trip);
        break;
      case "delete":
        delete(trip);
        break;
      default:
    }
  }

  private void saveOrUpdate(Trip trip) {
    System.out.println("received in 'saveOrUpdate': " + trip);
    tripRepo.save(enrich(trip));
  }

  private void delete(Trip trip) {
    System.out.println("received in 'delete': " + trip);
    tripRepo.delete(trip);
  }

  private Trip enrich(Trip trip) {
    // TODO: enriching

    return trip;
  }
}
