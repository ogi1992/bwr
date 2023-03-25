package com.example.bwraudit.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogMessage {

  private LocalDateTime dateTime;
  private Integer taskId;
  private String actionType;
  private Integer userId;
}
