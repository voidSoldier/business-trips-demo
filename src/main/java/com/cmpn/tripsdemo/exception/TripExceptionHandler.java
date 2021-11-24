package com.cmpn.tripsdemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TripExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(TripExceptionHandler.class);

  @ExceptionHandler(TripNotFoundException.class)
  ResponseEntity<String> handleTripNotFoundByProvidedIdException(TripNotFoundException exception) {
    log.error(exception.getMessage());
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }
}
