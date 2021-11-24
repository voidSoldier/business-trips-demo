package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.domain.Trip;
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

  private static final Logger log = LoggerFactory.getLogger(TripService.class);

  public TripService(TripMongoRepo tripRepo, Producer producer) {
    this.tripRepo = tripRepo;
    this.producer = producer;
  }

  public Page<Trip> findAll(Pageable pageable) {
    return tripRepo.findAll(pageable);
  }

  public Trip findById(String id) throws TripNotFoundException {
    log.info("Getting trip with id[{}]", id);
    return tripRepo.findById(id).orElseThrow(() -> new TripNotFoundException("Entity with id [" + id + "] doesn't exist."));
  }

  public void saveOrUpdateTrip(Trip trip) {
    producer.sendWrappedMsg("save-upd000", trip);
    log.info("Saving trip [{}]", trip);
  }

  public void deleteTrip(Trip trip) {
    producer.sendWrappedMsg("delete", trip);
    log.info("Deleting trip [{}]", trip);
  }
}
