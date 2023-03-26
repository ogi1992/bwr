package com.example.bwr.services;

import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.producers.AuditLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

  private final AuditLogProducer auditLogProducer;

  public void logEvent(AuditLogMessage auditLogMessage, Integer robotId) {
    auditLogProducer.sendMessage(auditLogMessage, robotId);
  }

}
