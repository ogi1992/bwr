package com.example.bwrrobot.services;

import com.example.bwrrobot.entities.RobotEntity;
import com.example.bwrrobot.enums.MessageType;
import com.example.bwrrobot.enums.RobotState;
import com.example.bwrrobot.exceptions.ExceptionSuppliers;
import com.example.bwrrobot.models.messages.KeepAliveMessage;
import com.example.bwrrobot.producers.KeepAliveProducer;
import com.example.bwrrobot.repositories.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeepAliveScheduledService {

  @Value("${robot-id}")
  private Integer robotId;

  private final RobotRepository robotRepository;
  private final KeepAliveProducer keepAliveProducer;

  // every 2 seconds
  @Scheduled(cron = "*/2 * * * * *")
  public void keepAlive() {
    RobotEntity robotEntity = robotRepository.findById(robotId)
        .orElseThrow(ExceptionSuppliers.robotNotFound);

    if (RobotState.ON.equals(robotEntity.getState())) {
      KeepAliveMessage keepAliveMessage = KeepAliveMessage.builder()
          .type(MessageType.KEEP_ALIVE)
          .sourceId(robotId)
          .build();

      keepAliveProducer.sendMessage(keepAliveMessage, robotId);
    }
  }
}
