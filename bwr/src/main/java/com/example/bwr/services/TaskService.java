package com.example.bwr.services;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.MessageType;
import com.example.bwr.enums.RobotState;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.models.Task;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.producers.TaskProducer;
import com.example.bwr.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final AuditLogService auditLogService;
  private final ValidationService validationService;
  private final TaskProducer taskProducer;
  private final RobotService robotService;

  public Task uploadTask(Task task) {
    validationService.validateRobotExists(task.getRobotId());

    TaskEntity taskEntity = TaskEntity.builder()
        .route(task.getRoute())
        .robotId(task.getRobotId())
        .name(task.getName())
        .build();

    TaskEntity savedTask = taskRepository.save(taskEntity);
    task.setId(savedTask.getId());

    auditLogService.logEvent(); // TODO

    return task;
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

      taskProducer.sendMessage(turnOnRobotMessage);
    }

    TaskMessage startCommandMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
        Command.START_COMMAND);

    taskProducer.sendMessage(startCommandMessage);
  }

  public void stopCommand(Integer taskId, Integer userId) {
    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(ExceptionSuppliers.taskNotFound);
    validationService.validateCommand(taskEntity.getStatus(), userId, Command.STOP_COMMAND);

    TaskMessage stopCommandMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
        Command.STOP_COMMAND);

    taskProducer.sendMessage(stopCommandMessage);
  }

  public void endTask(Integer taskId, Integer userId) {
    TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(ExceptionSuppliers.taskNotFound);
    validationService.validateCommand(taskEntity.getStatus(), userId, Command.END_TASK);

    TaskMessage endTaskMessage = TaskMessage.buildTaskMessage(taskId, userId, taskEntity.getRobotId(),
        Command.END_TASK);

    taskProducer.sendMessage(endTaskMessage);
  }
}
