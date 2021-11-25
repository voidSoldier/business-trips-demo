package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.datasource.TripService;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TripServiceIntegrationTest {

  @Mock
  private TripMongoRepo repo;

  @InjectMocks
  private TripService service;

  @Test
  void shouldFindTripById() throws TripNotFoundException {
    Trip trip = new Trip();
    trip.setId("1");
    when(repo.findById("1")).thenReturn(Optional.of(trip));

    Assertions.assertEquals(trip, service.findById("1"));
  }

  @Test
  void shouldThrowExceptionForTripInvalidId() {
    Assertions.assertThrows(TripNotFoundException.class, () -> service.findById("1"));
  }
}
