package com.cmpn.tripsdemo.exception;

public class TripNotFoundException extends Throwable {
  public TripNotFoundException(String message) {
    super(message);
  }
}

