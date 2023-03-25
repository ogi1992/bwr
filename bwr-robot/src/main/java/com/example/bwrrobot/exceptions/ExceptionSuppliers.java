package com.example.bwrrobot.exceptions;

import java.util.function.Supplier;

public class ExceptionSuppliers {

  public static final Supplier<ValidationException> robotNotFound = () -> ValidationException.builder()
      .message("No existing robot found for given ID!")
      .build();
}
