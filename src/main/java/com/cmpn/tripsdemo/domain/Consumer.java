package com.cmpn.tripsdemo.domain;

import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private TripMongoRepo tripRepo;
    private FeignClient client;

    public Consumer(TripMongoRepo tripRepo, FeignClient client) {
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
