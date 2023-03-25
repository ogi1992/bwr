package com.example.bwrrobot.models.messages;

import com.example.bwrrobot.enums.Command;
import com.example.bwrrobot.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TaskMessage extends Message {

  private Integer id;
  private Integer sourceId;
  private Integer targetId;
  private Command command;

  public TaskMessage reverseUserType(MessageType messageType) {
    return TaskMessage.builder()
        .id(this.id)
        .sourceId(this.targetId)
        .targetId(this.sourceId)
        .command(this.command)
        .type(messageType)
        .build();
  }
}
