package com.example.bwr.services.handlers;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.RobotState;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.repositories.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TurnOnRobotHandler implements Handler {

  private final RobotRepository robotRepository;

  @Override
  public void handle(TaskMessage message) {
    RobotEntity robotEntity = robotRepository.findById(message.getSourceId())
        .orElseThrow(ExceptionSuppliers.robotNotFound);
    robotEntity.setState(RobotState.ON);

    robotRepository.save(robotEntity);
  }

  @Override
  public Command getCommand() {
    return Command.TURN_ON_ROBOT;
  }
}
