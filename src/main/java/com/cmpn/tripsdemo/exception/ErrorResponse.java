package com.cmpn.tripsdemo.exception;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

  private String errorCode;

  private String errorMsg;

  public ErrorResponse() {}

  public ErrorResponse(String errorCode, String errorMsg) {
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
      "errorCode='" + errorCode + '\'' +
      ", errorMsg='" + errorMsg + '\'' +
      '}';
  }
}
