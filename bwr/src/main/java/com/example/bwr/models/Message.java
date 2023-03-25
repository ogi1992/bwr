package com.example.bwr.models;

import com.example.bwr.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Message {

  private MessageType type;
}
