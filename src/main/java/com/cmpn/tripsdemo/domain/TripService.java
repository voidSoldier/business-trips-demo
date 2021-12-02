package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.datasource.Producer;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {

  private final TripMongoRepo tripRepo;

  private final Producer producer;

  private final Enricher enricher;

  private static final Logger log = LoggerFactory.getLogger(TripService.class);

  public TripService(TripMongoRepo tripRepo, Producer producer, Enricher enricher) {
    this.tripRepo = tripRepo;
    this.producer = producer;
    this.enricher = enricher;
  }

  // through queue
  public void saveOrUpdateTrip(Trip trip) {
    log.info("Saving trip [{}]", trip);
    producer.sendWrappedMsg("save-upd", trip);
  }

  public void deleteTrip(Trip trip) {
    log.info("Deleting trip [{}]", trip);
    producer.sendWrappedMsg("delete", trip);
  }

  // directly to repo
  public Page<Trip> findAll(Pageable pageable) {
    return tripRepo.findAll(pageable);
  }

  public Trip findById(String id) throws TripNotFoundException {
    log.info("Getting trip with id[{}]", id);
    return tripRepo.findById(id).orElseThrow(() -> new TripNotFoundException("Trip with id [" + id + "] doesn't exist."));
  }

  public void saveToDb(Trip trip) {
    tripRepo.save(trip.getLocation() != null ? enricher.enrich(trip) : trip);
  }

  public void deleteFromDb(Trip trip) {
    tripRepo.delete(trip);
  }
}
