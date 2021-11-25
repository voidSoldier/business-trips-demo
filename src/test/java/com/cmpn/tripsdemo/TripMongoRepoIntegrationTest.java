package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataMongoTest
public class TripMongoRepoIntegrationTest {

  @Autowired
  TripMongoRepo repo;
  private static String TRIP_ID;

  @BeforeEach
  void setUp() {
    Trip trip1 = new Trip("trip 1", null, null, "user 1");
    Trip trip2 = new Trip("trip 2", null, null, "user 2");
    List<Trip> saved = repo.saveAll(Arrays.asList(trip1, trip2));
    TRIP_ID = saved.get(0).getId();
  }

  @Test
  @DisplayName("Should return all trips")
  void shouldReturnAllTrips() {
    List<Trip> result = repo.findAll();
    Assertions.assertThat(result).isNotEmpty();
  }

  @Test
  @DisplayName("Should return trip by id")
  void shouldReturnTripById() {
    Optional<Trip> found = repo.findById(TRIP_ID);
    Assertions.assertThat(found).isPresent();
    Assertions.assertThat(found.get().getId()).isEqualTo(TRIP_ID);
  }

  @Test
  @DisplayName("Should save trip")
  void shouldSaveTrip() {
    Trip newTrip = new Trip();
    newTrip.setDestTitle("new trip");
    String savedId = repo.save(newTrip).getId();
    Optional<Trip> saved = repo.findById(savedId);
    Assertions.assertThat(saved).isPresent();
    Assertions.assertThat(saved.get().getDestTitle()).isEqualTo(newTrip.getDestTitle());
  }

  @Test
  @DisplayName("Should update trip")
  void shouldUpdateTrip() {
      Trip trip = repo.findById(TRIP_ID).get();
      trip.setDestTitle("updated");
      repo.save(trip);
      Assertions.assertThat(repo.findById(TRIP_ID).get().getDestTitle()).isEqualTo("updated");
  }

  @Test
  @DisplayName("Should delete trip")
  void shouldDeleteTrip() {
    Trip trip = repo.findById(TRIP_ID).get();
    repo.delete(trip);
    Assertions.assertThat(repo.findById(TRIP_ID)).isNotPresent();
  }
}
