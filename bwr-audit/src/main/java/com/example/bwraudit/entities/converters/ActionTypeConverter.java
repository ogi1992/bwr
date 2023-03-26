package com.example.bwraudit.entities.converters;

import com.example.bwraudit.enums.ActionType;
import jakarta.persistence.AttributeConverter;

public class ActionTypeConverter implements AttributeConverter<ActionType, String> {

  @Override
  public String convertToDatabaseColumn(ActionType actionType) {
    return actionType.name();
  }

  @Override
  public ActionType convertToEntityAttribute(String dbData) {
    return ActionType.valueOf(dbData);
  }
}
