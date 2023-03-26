package com.example.bwrrobot.listeners;

import com.example.bwrrobot.enums.MessageType;
import com.example.bwrrobot.models.messages.Message;
import com.example.bwrrobot.models.messages.TaskMessage;
import com.example.bwrrobot.producers.AckProducer;
import com.example.bwrrobot.services.RobotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskListener {

  private final ObjectMapper objectMapper;
  private final AckProducer ackProducer;
  private final RobotService robotService;

  @KafkaListener(topics = "${kafka.task.topic-name}", groupId = "${kafka.task.group-id}",
      concurrency = "${kafka.concurrency}", containerFactory = "kafkaListenerContainerFactory")
  public void listen(String message) {
    TaskMessage taskMessage = convertToTaskMessage(message);

    acknowledge(taskMessage);

    Integer robotId = taskMessage.getTargetId();
    robotService.executeCommand(robotId, taskMessage.getCommand(), taskMessage.getId());
  }

  public void acknowledge(Message message) {
    TaskMessage taskMessage = (TaskMessage) message;

    TaskMessage receiveMessage = taskMessage.reverseUserType(MessageType.RECEIVED);
    ackProducer.sendMessage(receiveMessage, receiveMessage.getSourceId());
  }

  public TaskMessage convertToTaskMessage(String message) {
    try {
      return objectMapper.readValue(message, TaskMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to TaskMessage");
    }
  }
}
