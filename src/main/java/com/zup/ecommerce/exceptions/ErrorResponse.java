package com.zup.ecommerce.exceptions;

public class ErrorResponse extends RuntimeException {
  public ErrorResponse(String message) {
    super(message);
  }
}
