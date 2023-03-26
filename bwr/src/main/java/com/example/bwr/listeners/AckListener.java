package com.example.bwr.listeners;

import com.example.bwr.models.TaskMessage;
import com.example.bwr.services.TaskMessageFactory;
import com.example.bwr.services.handlers.Handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AckListener {

  private final ObjectMapper objectMapper;
  private final TaskMessageFactory taskMessageFactory;

  @KafkaListener(topics = "${kafka.ack.topic-name}", groupId = "${kafka.ack.group-id}", concurrency = "${kafka.concurrency}",
      containerFactory = "kafkaListenerContainerFactory")
  public void listen(String message) {
    TaskMessage messageModel = convertToMessage(message);

    Handler handler = taskMessageFactory.getHandler(messageModel.getCommand());
    handler.handle(messageModel);
  }

  public TaskMessage convertToMessage(String message) {
    try {
      return objectMapper.readValue(message, TaskMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to TaskMessage");
    }
  }
}
