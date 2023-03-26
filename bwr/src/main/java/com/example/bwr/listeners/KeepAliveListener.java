package com.example.bwr.listeners;

import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.UserType;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.models.KeepAliveMessage;
import com.example.bwr.services.AuditLogService;
import com.example.bwr.services.handlers.KeepAliveMessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeepAliveListener {

  private final ObjectMapper objectMapper;
  private final KeepAliveMessageHandler keepAliveMessageHandler;
  private final AuditLogService auditLogService;

  @KafkaListener(topics = "${kafka.keep-alive.topic-name}", groupId = "${kafka.keep-alive.group-id}", concurrency = "${kafka.concurrency}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(String message) {
    KeepAliveMessage messageModel = convertToMessage(message);

    keepAliveMessageHandler.handle(messageModel);

    AuditLogMessage auditLogMessage = AuditLogMessage.buildAuditLogMessage(LocalDateTime.now(), null,
        ActionType.KEEP_ALIVE, messageModel.getSourceId(), UserType.ROBOT, null, null);

    auditLogService.logEvent(auditLogMessage, messageModel.getSourceId());
  }

  public KeepAliveMessage convertToMessage(String message) {
    try {
      return objectMapper.readValue(message, KeepAliveMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to KeepAliveMessage");
    }
  }
}
