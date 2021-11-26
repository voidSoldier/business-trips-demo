package com.cmpn.tripsdemo.domain;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Document
public class Trip implements Serializable {

  @Id
  private String id;

  @Max(255) // TODO: rename to 'tripTitle'
  private String title;

  private LocalDateTime departure;

  private LocalDateTime arrival;

  @NotNull
  private String userID;

  private Map<String, String> segments;

  private Location location;

  public Trip() {
  }

  public Trip(String title, LocalDateTime departure, LocalDateTime arrival,
              @NonNull String userID, Location location) {
    this.title = title;
    this.departure = departure;
    this.arrival = arrival;
    this.userID = userID;
    this.location = location;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDateTime getDeparture() {
    return departure;
  }

  public void setDeparture(LocalDateTime departure) {
    this.departure = departure;
  }

  public LocalDateTime getArrival() {
    return arrival;
  }

  public void setArrival(LocalDateTime arrival) {
    this.arrival = arrival;
  }

  @NonNull
  public String getUserID() {
    return userID;
  }

  public void setUserID(@NonNull String userID) {
    this.userID = userID;
  }

  public Map<String, String> getSegments() {
    return segments;
  }

  public void setSegments(Map<String, String> segments) {
    this.segments = segments;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "Trip{" +
      "id='" + id + '\'' +
      ", title='" + title + '\'' +
      ", userID='" + userID + '\'' +
      '}';
  }
}
