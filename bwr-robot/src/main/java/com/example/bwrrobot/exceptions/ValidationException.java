package com.example.bwrrobot.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationException extends RuntimeException {

  private String message;

  public ValidationException(String errorMessage) {
    super(errorMessage);
    this.message = errorMessage;
  }
}
