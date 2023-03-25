package com.example.bwrrobot.services;

import com.example.bwrrobot.entities.RobotEntity;
import com.example.bwrrobot.enums.Command;
import com.example.bwrrobot.exceptions.ExceptionSuppliers;
import com.example.bwrrobot.repositories.RobotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RobotService {

  private final RobotRepository robotRepository;

  public void executeCommand(Integer robotId, Command command) {
    RobotEntity robotEntity = robotRepository.findById(robotId)
        .orElseThrow(ExceptionSuppliers.robotNotFound);

    log.info("Robot : " + robotEntity.getName() + "executed command: " + command);
  }
}
