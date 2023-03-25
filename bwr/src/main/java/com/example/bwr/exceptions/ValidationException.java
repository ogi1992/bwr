package com.example.bwr.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ValidationException extends RuntimeException {

  private HttpStatus httpStatus;
  private String message;

  public ValidationException(HttpStatus httpStatus, String errorMessage) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.message = errorMessage;
  }
}
