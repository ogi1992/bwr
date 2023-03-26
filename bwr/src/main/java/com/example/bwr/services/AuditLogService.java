package com.example.bwr.services;

import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.producers.BWRProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

  private final BWRProducer producer;

  public void logEvent(AuditLogMessage auditLogMessage) {
    producer.sendMessage(auditLogMessage);
  }

}
