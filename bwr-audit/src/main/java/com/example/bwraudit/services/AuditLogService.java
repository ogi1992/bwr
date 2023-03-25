package com.example.bwraudit.services;

import com.example.bwraudit.entities.AuditLogEntity;
import com.example.bwraudit.models.AuditLogMessage;
import com.example.bwraudit.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

  private final AuditLogRepository auditLogRepository;

  public void logEvent(AuditLogMessage message) {
    AuditLogEntity auditLogEntity = AuditLogEntity.builder()
        .dateTime(message.getDateTime())
        .taskId(message.getTaskId())
        .actionType(message.getActionType())
        .userId(message.getUserId())
        .build();

    auditLogRepository.save(auditLogEntity);
  }

}
