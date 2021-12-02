package com.cmpn.tripsdemo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Enricher {

  private final CustomFeignClient client;

  private static final Logger log = LoggerFactory.getLogger(Enricher.class);

  public Enricher(CustomFeignClient client) {
    this.client = client;
  }

  public Trip enrich(Trip trip) {
    Location location = trip.getLocation();
    String locationName = location.getName();
    ObjectMapper m = new ObjectMapper();

    try {
      String json = client.getLocationCoordinates(locationName, "json", "1");
      JsonNode root = m.readTree(json);
      JsonNode lat = root.findValue("lat");
      JsonNode lon = root.findValue("lon");

      if (lat == null || lon == null)
        throw new UnsupportedOperationException();

      location.setLat(lat.textValue());
      location.setLon(lon.textValue());
      log.info("Enriched trip with {}", location);
    } catch (JsonProcessingException | UnsupportedOperationException e) {
      log.error("Error getting coordinates for location[{}]", locationName);
    }

    return trip;
  }
}
