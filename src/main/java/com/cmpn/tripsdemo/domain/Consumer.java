package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.TripMongoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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

    // default
    public void getMessage(String msg) {
        System.out.println("received in 'getMessage': " + msg);
    }

    public void saveOrUpdate(String tripStr) throws JsonProcessingException {
      ObjectMapper m = new ObjectMapper();
      Trip trip = m.readValue(tripStr, Trip.class);
      System.out.println("received in 'saveOrUpdate': " + trip);
      tripRepo.save(enrich(trip));

    }

    public void delete(String id) {
      System.out.println("received in 'delete': " + id);
        tripRepo.deleteById(id);
    }

    private Trip enrich(Trip trip) {
        // TODO: enriching

        return trip;
    }
}
