package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

  private TestRestTemplate template = new TestRestTemplate();

  protected static final String TOKEN = "Bearer auth-token";

  private static final String URL = "http://localhost:8080/api/trip";

  @Test
  void shouldReturnAllTrips() throws URISyntaxException {
    ResponseEntity<Object> response =
      template.exchange(uri(), HttpMethod.GET, new HttpEntity<>(authHeader()), Object.class);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(response.hasBody());
  }

  @Test
  void shouldReturnTripById() throws URISyntaxException {
    Map<String, String> params = new HashMap<>();
    params.put("id", "619cafe8f256c84eb657b60d"); // temporary
    ResponseEntity<Trip> response =
      template.exchange(uri() + "/{id}", HttpMethod.GET, new HttpEntity<>(authHeader()), Trip.class, params);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(response.hasBody());
  }

  @Test
  void shouldReturnExceptionForTripInvalidId() throws URISyntaxException {
    Map<String, String> params = new HashMap<>();
    params.put("id", "1");
    ResponseEntity<TripNotFoundException> response =
      template.exchange(uri() + "/{id}", HttpMethod.GET, new HttpEntity<>(authHeader()), TripNotFoundException.class, params);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(response.hasBody());
  }

  @Test
  void shouldCreateTrip() throws URISyntaxException {
    ResponseEntity<Trip> response = template.exchange(uri(), HttpMethod.POST, new HttpEntity<>(new Trip(), authHeader()), Trip.class);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }

  @Test
  void shouldUpdateTrip() throws URISyntaxException {
    Trip trip = new Trip();
    trip.setId("619b5320a73a127faa92d3b4"); // temporary
    ResponseEntity<Void> response = template.exchange(uri(), HttpMethod.PUT, new HttpEntity<>(trip, authHeader()), Void.class);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    Assertions.assertFalse(response.hasBody());
  }

  @Test
  void shouldDeleteTripById() throws URISyntaxException {
    Trip trip = new Trip();
    trip.setId("619b5320a73a127faa92d3b4"); // temporary
    ResponseEntity<Void> response = template.exchange(uri(), HttpMethod.DELETE, new HttpEntity<>(trip, authHeader()), Void.class);
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    Assertions.assertFalse(response.hasBody());
  }

  private HttpHeaders authHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
    return headers;
  }

  private URI uri() throws URISyntaxException {
    return new URI(URL);
  }
}
