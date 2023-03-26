package com.example.bwraudit.models;

import com.example.bwraudit.enums.ActionType;
import com.example.bwraudit.enums.UserType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogMessage implements Serializable {

  private LocalDateTime dateTime;
  private Integer taskId;
  private ActionType actionType;
  private Integer sourceId;
  private UserType sourceType;
  private Integer targetId;
  private UserType targetType;
}
