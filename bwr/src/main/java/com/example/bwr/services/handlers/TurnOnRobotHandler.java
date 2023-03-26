package com.example.bwr.services.handlers;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.RobotState;
import com.example.bwr.enums.UserType;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.repositories.RobotRepository;
import com.example.bwr.services.AuditLogService;
import com.example.bwr.services.TaskService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TurnOnRobotHandler implements Handler {

  private final RobotRepository robotRepository;
  private final TaskService taskService;
  private final AuditLogService auditLogService;

  @Override
  public void handle(TaskMessage message) {
    RobotEntity robotEntity = robotRepository.findById(message.getSourceId())
        .orElseThrow(ExceptionSuppliers.robotNotFound);
    robotEntity.setState(RobotState.ON);

    robotRepository.save(robotEntity);

    AuditLogMessage auditLogMessage = AuditLogMessage.buildAuditLogMessage(LocalDateTime.now(), message.getId(),
        ActionType.TURN_ON_ROBOT_ACK, message.getSourceId(), UserType.ROBOT, message.getTargetId(), UserType.USER);

    auditLogService.logEvent(auditLogMessage, message.getSourceId());

    // now that robot is in state ON, we can proceed with starting the command
    taskService.startCommand(message.getId(), message.getTargetId());
  }

  @Override
  public Command getCommand() {
    return Command.TURN_ON_ROBOT;
  }
}
