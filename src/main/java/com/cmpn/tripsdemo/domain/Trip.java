package com.cmpn.tripsdemo.domain;

import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Document
public class Trip {
    @MongoId
    private String id;

    @Max(255)
    private String destTitle;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    @NotNull
    private String userID;

    private Map<String, String> coordinates;

    @NotNull
    @NotEmpty
    private Map<String, String> segments;

    public Trip(String destTitle, LocalDateTime departure, LocalDateTime arrival, @NonNull String userID) {
        this.destTitle = destTitle;
        this.departure = departure;
        this.arrival = arrival;
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestTitle() {
        return destTitle;
    }

    public void setDestTitle(String destTitle) {
        this.destTitle = destTitle;
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

    public Map<String, String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Map<String, String> coordinates) {
        this.coordinates = coordinates;
    }

    public Map<String, String> getSegments() {
        return segments;
    }

    public void setSegments(Map<String, String> segments) {
        this.segments = segments;
    }
}