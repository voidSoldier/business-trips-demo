package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.TripMongoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private TripMongoRepo tripRepo;

  private CustomFeignClient client;

  private static final Logger log = LoggerFactory.getLogger(Consumer.class);

  public Consumer(TripMongoRepo tripRepo, CustomFeignClient client) {
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

  public void saveOrUpdate(Trip trip) {
    tripRepo.save(trip.getLocation() != null ? enrich(trip) : trip);
  }

  public void delete(Trip trip) {
    tripRepo.delete(trip);
  }

  private Trip enrich(Trip trip) {
    Location location = trip.getLocation();
    String locationName = location.getName();
    ObjectMapper m = new ObjectMapper();

    try {
      String json = client.getLocationCoordinates(locationName, "json", "1");
      JsonNode root = m.readTree(json);
      JsonNode lat = root.findValue("lat");
      JsonNode lon = root.findValue("lon");

      if (lat == null || lon == null)
        throw new UnsupportedOperationException();

      location.setLat(lat.textValue());
      location.setLon(lon.textValue());
    } catch (JsonProcessingException | UnsupportedOperationException e) {
      log.error("Error getting coordinates for location[{}]", locationName);
    }

    return trip;
  }
}
