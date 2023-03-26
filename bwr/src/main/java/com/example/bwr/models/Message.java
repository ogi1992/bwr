package com.example.bwr.models;

import com.example.bwr.enums.MessageType;
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

  private MessageType type;
}
