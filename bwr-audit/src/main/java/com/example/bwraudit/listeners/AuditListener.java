package com.example.bwraudit.listeners;

import com.example.bwraudit.models.AuditLogMessage;
import com.example.bwraudit.services.AuditLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditListener {

  private final ObjectMapper objectMapper;
  private final AuditLogService auditLogService;

  @KafkaListener(topics = "${kafka.audit.topic-name}", groupId = "${kafka.audit.group-id}", concurrency = "${kafka.concurrency}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(String message) {
    AuditLogMessage auditLogMessage = convertToMessage(message);

    auditLogService.logEvent(auditLogMessage);
  }

  private AuditLogMessage convertToMessage(String message) {
    try {
      return objectMapper.readValue(message, AuditLogMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to AuditLogMessage");
    }
  }
}
