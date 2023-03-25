package com.example.bwr.exceptions;

import lombok.Builder;

@Builder
public class ScheduleJobException extends RuntimeException {

  private String message;

  public ScheduleJobException(String message) {
    super(message);
    this.message = message;
  }
}
