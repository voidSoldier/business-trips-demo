package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.MongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private MongoRepo tripRepo;
    private FeignClient client;

    public Consumer(MongoRepo tripRepo, FeignClient client) {
        this.tripRepo = tripRepo;
        this.client = client;
    }

    public void saveOrUpdate(Trip trip) {
        tripRepo.save(enrich(trip));
    }

    public void delete(Trip trip) {
        tripRepo.delete(enrich(trip));
    }

    private Trip enrich(Trip trip) {
        // TODO: enriching

        return trip;
    }
}
