package com.example.bwr.listeners;

import com.example.bwr.enums.MessageType;
import com.example.bwr.models.Message;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.services.TaskMessageFactory;
import com.example.bwr.services.handlers.Handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskListener extends Listener {

  private final ObjectMapper objectMapper;
  private final TaskMessageFactory taskMessageFactory;

  public void listenTopic(Message message) {
    TaskMessage taskMessage = (TaskMessage) message;

    Handler handler = taskMessageFactory.getHandler(taskMessage.getCommand());
    handler.handle(taskMessage);
  }

  public boolean accept(MessageType type) {
    return MessageType.RECEIVED.equals(type);
  }

  public TaskMessage convertToMessage(String message) {
    try {
      return objectMapper.readValue(message, TaskMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to TaskMessage");
    }
  }
}
