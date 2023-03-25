package com.example.bwr.services;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.enums.RobotState;
import com.example.bwr.repositories.RobotRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotService {

  private final RobotRepository robotRepository;

  public boolean checkIfRobotExists(Integer robotId) {
    return robotRepository.existsById(robotId);
  }

  public Optional<RobotEntity> findById(Integer robotId) {
    return robotRepository.findById(robotId);
  }

  public void updateState(Integer robotId, RobotState robotState) {
    robotRepository.updateRobotStateById(robotId, robotState);
  }
}
