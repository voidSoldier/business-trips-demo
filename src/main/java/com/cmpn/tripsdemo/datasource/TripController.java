package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
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

  private final TripService tripService;

  public TripController(Producer producer, TripService tripService) {
    this.producer = producer;
    this.tripService = tripService;
  }

  @GetMapping
  public Page<Trip> getAll(Pageable pageable) {
    return tripService.findAll(pageable);
  }

  @GetMapping(path = "/{id}")
  public Trip getTripById(@PathVariable String id) throws TripNotFoundException {
    return tripService.findById(id);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void createTrip(@RequestBody Trip trip) {
    producer.sendWrappedMsg("save-upd", trip);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void updateTrip(@RequestBody Trip trip) {
    producer.sendWrappedMsg("save-upd", trip);
  }

  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void deleteTrip(@RequestBody Trip trip) {
    producer.sendWrappedMsg("delete", trip);
  }
}
