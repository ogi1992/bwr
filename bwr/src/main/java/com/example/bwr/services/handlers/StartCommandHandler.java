package com.example.bwr.services.handlers;

import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.Status;
import com.example.bwr.enums.UserType;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.repositories.TaskRepository;
import com.example.bwr.services.AuditLogService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartCommandHandler implements Handler {

  private final TaskRepository taskRepository;
  private final AuditLogService auditLogService;

  @Override
  public void handle(TaskMessage message) {
    TaskEntity taskEntity = taskRepository.findById(message.getId())
        .orElseThrow(ExceptionSuppliers.taskNotFound);
    taskEntity.setStatus(Status.IN_PROGRESS);

    taskRepository.save(taskEntity);

    AuditLogMessage auditLogMessage = AuditLogMessage.buildAuditLogMessage(LocalDateTime.now(), message.getId(),
        ActionType.START_COMMAND, message.getSourceId(), UserType.ROBOT, message.getTargetId(), UserType.USER);

    auditLogService.logEvent(auditLogMessage);
  }

  @Override
  public Command getCommand() {
    return Command.START_COMMAND;
  }
}
