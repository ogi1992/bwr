package com.example.bwr.utils;

import com.example.bwr.enums.RobotState;
import com.example.bwr.services.RobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeepAliveThread extends Thread {

  private Integer robotId;

  @Autowired
  private RobotService robotService;

  public KeepAliveThread(Integer robotId, RobotService robotService) {
    this.robotId = robotId;
    this.robotService = robotService;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      log.info("Keep alive counter interrupted. Robot is alive");
      return;
    }

    robotService.updateState(robotId, RobotState.OFF);
  }
}
