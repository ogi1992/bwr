package com.example.bwraudit.entities.converters;

import com.example.bwraudit.enums.UserType;
import jakarta.persistence.AttributeConverter;

public class UserTypeConverter implements AttributeConverter<UserType, String> {

  @Override
  public String convertToDatabaseColumn(UserType userType) {
    return userType.name();
  }

  @Override
  public UserType convertToEntityAttribute(String userType) {
    return UserType.valueOf(userType);
  }
}
