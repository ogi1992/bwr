package com.example.bwr.services;

import static com.example.bwr.utils.TestUtils.ROBOT_ID;
import static org.mockito.Mockito.verify;

import com.example.bwr.enums.RobotState;
import com.example.bwr.repositories.RobotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RobotServiceTest {

  @InjectMocks
  private RobotService robotService;
  @Mock
  private RobotRepository robotRepository;

  @Test
  void findById() {
    robotService.findById(ROBOT_ID);

    verify(robotRepository).findById(ROBOT_ID);
  }

  @Test
  void updateState() {
    robotService.updateState(ROBOT_ID, RobotState.OFF);

    verify(robotRepository).updateRobotStateById(ROBOT_ID, RobotState.OFF);
  }
}