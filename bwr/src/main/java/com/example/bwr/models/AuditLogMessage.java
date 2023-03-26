package com.example.bwr.models;

import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.UserType;
import com.example.bwr.enums.MessageType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AuditLogMessage extends Message {

  private LocalDateTime dateTime;
  private Integer taskId;
  private ActionType actionType;
  private Integer sourceId;
  private UserType sourceType;
  private Integer targetId;
  private UserType targetType;

  public static AuditLogMessage buildAuditLogMessage(LocalDateTime dateTime, Integer taskId, ActionType actionType,
      Integer sourceId, UserType sourceType, Integer targetId, UserType targetType) {
    
    return AuditLogMessage.builder()
        .dateTime(dateTime)
        .taskId(taskId)
        .actionType(actionType)
        .sourceId(sourceId)
        .sourceType(sourceType)
        .targetId(targetId)
        .targetType(targetType)
        .type(MessageType.AUDIT)
        .build();
  }
}
