package com.cmpn.tripsdemo.repos;

import com.cmpn.tripsdemo.domain.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripMongoRepo extends MongoRepository<Trip, String> {
}
