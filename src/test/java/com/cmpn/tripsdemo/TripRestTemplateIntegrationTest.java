package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.ErrorResponse;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TripRestTemplateIntegrationTest {

  TestRestTemplate template = new TestRestTemplate();

  @Autowired
  TripMongoRepo repo;

  private static final String TOKEN = "auth-token";

  private static final String BEARER = "Bearer ";

  private static String TRIP_ID = "";

  private static final String TEST_TITLE = "TEST";

  private Trip trip;

  private static final String URL = "http://localhost:8080/api/trip";

  @BeforeEach
  void setUp() {
    trip = new Trip();
    trip.setTitle(TEST_TITLE);
    Trip saved = repo.save(trip);
    TRIP_ID = saved.getId();
  }

  @AfterEach
  void cleaning() {
    repo.deleteAllByTitle(TEST_TITLE);
  }

  @Test
  void shouldReturnAllTrips() throws URISyntaxException {
    ResponseEntity<Object> response =
      template.exchange(uri(), HttpMethod.GET, new HttpEntity<>(authHeader()), Object.class);
//      template.exchange(uri(), HttpMethod.GET, new HttpEntity<>(authHeader()), new ParameterizedTypeReference<>() {});
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertTrue(response.hasBody());
  }

  @Test
  void shouldReturnTripById() throws URISyntaxException {
    Map<String, String> params = new HashMap<>();
    params.put("id", TRIP_ID);
    ResponseEntity<Trip> response =
      template.exchange(uri() + "/{id}", HttpMethod.GET, new HttpEntity<>(authHeader()), Trip.class, params);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertTrue(response.hasBody());
    Assertions.assertNotNull(response.getBody().getId());
  }

  @Test
  void shouldReturnExceptionForTripInvalidId() throws URISyntaxException {
    Map<String, String> params = new HashMap<>();
    params.put("id", "fake_id");
    ResponseEntity<ErrorResponse> response =
      template.exchange(uri() + "/{id}", HttpMethod.GET, new HttpEntity<>(authHeader()), ErrorResponse.class, params);
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Assertions.assertTrue(response.hasBody());
  }

  @Test
  void shouldCreateTrip() throws URISyntaxException {
    ResponseEntity<Trip> response = template.exchange(uri(), HttpMethod.POST, new HttpEntity<>(trip, authHeader()), Trip.class);
    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void shouldUpdateTrip() throws URISyntaxException {
    ResponseEntity<Void> response = template.exchange(uri(), HttpMethod.PUT, new HttpEntity<>(trip, authHeader()), Void.class);
    Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    Assertions.assertFalse(response.hasBody());
  }

  @Test
  void shouldDeleteTripById() throws URISyntaxException {
    ResponseEntity<Void> response = template.exchange(uri(), HttpMethod.DELETE, new HttpEntity<>(trip, authHeader()), Void.class);
    Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    Assertions.assertFalse(response.hasBody());
  }

  private HttpHeaders authHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, BEARER + TOKEN);
    return headers;
  }

  private URI uri() throws URISyntaxException {
    return new URI(URL);
  }
}
