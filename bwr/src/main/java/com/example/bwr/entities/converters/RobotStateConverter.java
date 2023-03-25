package com.example.bwr.entities.converters;

import com.example.bwr.enums.RobotState;
import jakarta.persistence.AttributeConverter;

public class RobotStateConverter implements AttributeConverter<RobotState, String> {

  @Override
  public String convertToDatabaseColumn(RobotState robotState) {
    return robotState.name();
  }

  @Override
  public RobotState convertToEntityAttribute(String state) {
    return RobotState.valueOf(state);
  }
}
