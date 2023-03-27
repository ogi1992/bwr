package com.example.bwr.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.example.bwr.producers.AuditLogProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

  @InjectMocks
  private AuditLogService auditLogService;
  @Mock
  private AuditLogProducer auditLogProducer;

  @Test
  void logEvent() {
    auditLogService.logEvent(any(), any());

    verify(auditLogProducer).sendMessage(any(), any());
  }
}