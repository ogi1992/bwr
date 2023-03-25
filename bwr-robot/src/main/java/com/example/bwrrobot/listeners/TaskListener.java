package com.example.bwrrobot.listeners;

import com.example.bwrrobot.enums.MessageType;
import com.example.bwrrobot.models.messages.Message;
import com.example.bwrrobot.models.messages.TaskMessage;
import com.example.bwrrobot.producers.TaskProducer;
import com.example.bwrrobot.services.RobotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskListener extends Listener {

  private final ObjectMapper objectMapper;
  private final TaskProducer taskProducer;
  private final RobotService robotService;

  public void listenTopic(Message message) {
    TaskMessage taskMessage = (TaskMessage) message;

    Integer robotId = taskMessage.getTargetId();
    robotService.executeCommand(robotId, taskMessage.getCommand());
  }

  public void acknowledge(Message message) {
    TaskMessage taskMessage = (TaskMessage) message;

    TaskMessage receiveMessage = taskMessage.reverseUserType(MessageType.RECEIVED);
    taskProducer.sendMessage(receiveMessage);
  }

  public boolean accept(MessageType type) {
    return MessageType.COMMAND.equals(type);
  }

  public TaskMessage convertToTaskMessage(String message) {
    try {
      return objectMapper.readValue(message, TaskMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to TaskMessage");
    }
  }
}
