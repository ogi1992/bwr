package com.example.bwr.models;

import com.example.bwr.enums.Command;
import com.example.bwr.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TaskMessage extends Message {

  private Integer id;
  private Integer sourceId;
  private Integer targetId;
  private Command command;

  public static TaskMessage buildTaskMessage(Integer taskId, Integer userId, Integer robotId, Command command) {
    return TaskMessage.builder()
        .id(taskId)
        .command(command)
        .sourceId(userId)
        .targetId(robotId)
        .type(MessageType.COMMAND)
        .build();
  }
}
