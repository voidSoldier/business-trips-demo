package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.config.RabbitConfig;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/trip", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {

  private final Producer producer;

  private final TripMongoRepo tripRepo;

  private final ObjectMapper mapper;

  public TripController(Producer producer, TripMongoRepo tripRepo, ObjectMapper mapper) {
    this.producer = producer;
    this.tripRepo = tripRepo;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<Trip> getAll(Pageable pageable) {
    return tripRepo.findAll(pageable);
  }

  @GetMapping(path = "/{id}")
  public Trip getTripById(@PathVariable String id) {
    return tripRepo.findById(id).orElseThrow(() ->
      new RuntimeException("Entity with id [" + id + "] doesn't exist."));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void createTrip(@RequestBody Trip newTrip) throws JsonProcessingException {
    producer.sendMessage(RabbitConfig.DIR_EXCHANGE_NAME, "trip.save", tripToJson(newTrip));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateTrip(@RequestBody Trip trip) throws JsonProcessingException {
    producer.sendMessage(RabbitConfig.DIR_EXCHANGE_NAME, "trip.update", tripToJson(trip));
  }

  @DeleteMapping(path = "/{id}")
  public void deleteTrip(@PathVariable String id) {
    producer.sendMessage(RabbitConfig.DIR_EXCHANGE_NAME, "trip.delete", id);
  }

  private String tripToJson(Trip trip) throws JsonProcessingException {
    return mapper.writeValueAsString(trip);
  }
}
