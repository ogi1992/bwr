package com.example.bwrrobot.models.messages;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class KeepAliveMessage extends Message {

  private Integer sourceId;
}
