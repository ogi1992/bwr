package com.example.bwrrobot.models.messages;

import com.example.bwrrobot.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Message {

  MessageType type;
}
