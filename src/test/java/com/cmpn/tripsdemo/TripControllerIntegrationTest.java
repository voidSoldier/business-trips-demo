package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.datasource.TripService;
import com.cmpn.tripsdemo.domain.Trip;
import com.cmpn.tripsdemo.exception.TripNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TripControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  TripService service;

  protected static final String BASE_URL = "/api/trip";

  protected static final String TOKEN = "Bearer auth-token";

  protected static final Trip trip;

  static {
    trip = new Trip();
    trip.setId("valid id");
    trip.setTitle("TEST");
  }

  @Test
  void shouldReturnAllTrips() throws Exception {
    when(service.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(trip)));

    mockMvc.perform(get(BASE_URL)
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void shouldReturnTripById() throws Exception {
    when(service.findById(any(String.class))).thenReturn(trip);

    mockMvc.perform(get(BASE_URL + "/1")
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
  }

  @Test
  void shouldNotReturnTripByInvalidId() throws Exception {
    when(service.findById(any())).thenThrow(new TripNotFoundException(""));

    mockMvc.perform(get(BASE_URL + "/1")
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isNotFound())
      .andExpect(mvcResult ->
        mvcResult.getResolvedException().getClass().equals(TripNotFoundException.class));
  }

  @Test
  void shouldCreateTrip() throws Exception {
    mockMvc.perform(post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(tripAsRequestBody())
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isCreated());
  }

  @Test
  void shouldUpdateTrip() throws Exception {
    mockMvc.perform(put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(tripAsRequestBody())
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isNoContent());
  }

  @Test
  void shouldDeleteTrip() throws Exception {
    mockMvc.perform(delete(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(tripAsRequestBody())
        .header(HttpHeaders.AUTHORIZATION, TOKEN))
      .andExpect(status().isNoContent());
  }

  private String tripAsRequestBody() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(trip);
  }
}
