package com.example.bwr.services;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.UserType;
import com.example.bwr.enums.MessageType;
import com.example.bwr.enums.RobotState;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.models.Task;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.producers.BWRProducer;
import com.example.bwr.repositories.TaskRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final AuditLogService auditLogService;
  private final ValidationService validationService;
  private final BWRProducer bwrProducer;
  private final RobotService robotService;

  public Task uploadTask(Task task, Integer userId) {
    validationService.validateRobotExists(task.getRobotId());

    TaskEntity taskEntity = TaskEntity.builder()
        .route(task.getRoute())
        .robotId(task.getRobotId())
        .name(task.getName())
        .build();

    TaskEntity savedTask = taskRepository.save(taskEntity);

    logEvent(savedTask.getId(), userId, savedTask.getRobotId(), ActionType.UPLOAD_TASK);
    return Task.buildTask(savedTask);
  }

  public void startCommand(Integer taskId, Integer userId) {
    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(ExceptionSuppliers.taskNotFound);
    validationService.validateCommand(taskEntity.getStatus(), userId, Command.START_COMMAND);

    RobotEntity robotEntity = robotService.findById(taskEntity.getRobotId())
        .orElseThrow(ExceptionSuppliers.robotNotFound);

    // if robot is in OFF state, we need to put it in ON state first
    if (RobotState.OFF.equals(robotEntity.getState())) {
      TaskMessage turnOnRobotMessage = TaskMessage.builder()
          .id(taskId)
          .command(Command.TURN_ON_ROBOT)
          .sourceId(userId)
          .targetId(taskEntity.getRobotId())
          .type(MessageType.COMMAND)
          .build();

      bwrProducer.sendMessage(turnOnRobotMessage, taskEntity.getRobotId());

      logEvent(taskId, userId, taskEntity.getRobotId(), ActionType.TURN_ON_ROBOT);
    } else {
      TaskMessage startCommandMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
          Command.START_COMMAND);
      bwrProducer.sendMessage(startCommandMessage, taskEntity.getRobotId());

      logEvent(taskId, userId, taskEntity.getRobotId(), ActionType.START_COMMAND);
    }
  }

  public void stopCommand(Integer taskId, Integer userId) {
    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(ExceptionSuppliers.taskNotFound);
    validationService.validateCommand(taskEntity.getStatus(), userId, Command.STOP_COMMAND);

    TaskMessage stopCommandMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
        Command.STOP_COMMAND);

    bwrProducer.sendMessage(stopCommandMessage, taskEntity.getRobotId());

    logEvent(taskId, userId, taskEntity.getRobotId(), ActionType.STOP_COMMAND);
  }

  public void endTask(Integer taskId, Integer userId) {
    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(ExceptionSuppliers.taskNotFound);
    validationService.validateCommand(taskEntity.getStatus(), userId, Command.END_TASK);

    TaskMessage endTaskMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
        Command.END_TASK);

    bwrProducer.sendMessage(endTaskMessage, taskEntity.getRobotId());

    logEvent(taskId, userId, taskEntity.getRobotId(), ActionType.END_TASK);
  }

  private void logEvent(Integer taskId, Integer userId, Integer robotId, ActionType actionType) {
    AuditLogMessage auditLogMessage = AuditLogMessage.buildAuditLogMessage(LocalDateTime.now(), taskId,
        actionType, userId, UserType.USER, robotId, UserType.ROBOT);

    auditLogService.logEvent(auditLogMessage, robotId);
  }
}
