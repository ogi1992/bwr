package com.example.bwr.entities.converters;

import com.example.bwr.enums.Status;
import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, String> {

  @Override
  public String convertToDatabaseColumn(Status status) {
    return status.name();
  }

  @Override
  public Status convertToEntityAttribute(String status) {
    return Status.valueOf(status);
  }
}
