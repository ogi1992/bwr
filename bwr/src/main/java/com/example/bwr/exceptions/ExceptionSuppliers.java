package com.example.bwr.exceptions;

import java.util.function.Supplier;
import org.springframework.http.HttpStatus;

public class ExceptionSuppliers {

  public static final Supplier<ValidationException> robotNotFound = () -> ValidationException.builder()
      .httpStatus(HttpStatus.NOT_FOUND)
      .message("No existing robot found for given ID!")
      .build();

  public static final Supplier<ValidationException> taskNotFound = () -> ValidationException.builder()
      .httpStatus(HttpStatus.NOT_FOUND)
      .message("No existing task found for given ID!")
      .build();

  public static final Supplier<ValidationException> userNotFound = () -> ValidationException.builder()
      .httpStatus(HttpStatus.NOT_FOUND)
      .message("No existing user found for given ID!")
      .build();

  public static final Supplier<ScheduleJobException> scheduleJobException = () -> ScheduleJobException.builder()
      .message("Exception occurred while scheduling a job")
      .build();
}
