package com.cmpn.tripsdemo.repos;

import com.cmpn.tripsdemo.domain.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepo extends MongoRepository<Trip, String> {
}
