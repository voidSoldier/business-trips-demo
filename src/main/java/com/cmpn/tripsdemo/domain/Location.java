package com.cmpn.tripsdemo.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Location implements Serializable {

  private String name;

  private String lat;

  private String lon;

  public Location() {
  }

  public Location(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLon() {
    return lon;
  }

  public void setLon(String lon) {
    this.lon = lon;
  }

  @Override
  public String toString() {
    return "Location{" +
      "name='" + name + '\'' +
      ", lat='" + lat + '\'' +
      ", lon='" + lon + '\'' +
      '}';
  }
}
