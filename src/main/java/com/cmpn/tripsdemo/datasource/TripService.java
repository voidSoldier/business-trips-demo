package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {

  private final TripMongoRepo tripRepo;

  public TripService(TripMongoRepo tripRepo) {
    this.tripRepo = tripRepo;
  }

  public Page<Trip> findAll(Pageable pageable) {
    return tripRepo.findAll(pageable);
  }

  public Trip findById(String id) throws TripNotFoundException {
    return tripRepo.findById(id).orElseThrow(() -> new TripNotFoundException("Entity with id [" + id + "] doesn't exist."));
  }
}
