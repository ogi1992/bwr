package com.example.bwrrobot.models.messages;

import com.example.bwrrobot.enums.MessageType;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Message implements Serializable {

  MessageType type;
}
