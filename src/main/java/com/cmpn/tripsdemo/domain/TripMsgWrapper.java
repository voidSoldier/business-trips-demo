package com.cmpn.tripsdemo.domain;

import java.io.Serializable;

public class TripMsgWrapper implements Serializable {

  private final Trip trip;
  private final String msgType;

  public TripMsgWrapper(Trip trip, String msgType) {
    this.trip = trip;
    this.msgType = msgType;
  }

  public Trip getTrip() {
    return trip;
  }

  public String getMsgType() {
    return msgType;
  }

  @Override
  public String toString() {
    return String.format("Msg type: %s, trip: %s", msgType, trip);
  }
}
