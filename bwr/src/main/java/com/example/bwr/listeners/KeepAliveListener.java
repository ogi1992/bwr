package com.example.bwr.listeners;

import com.example.bwr.enums.MessageType;
import com.example.bwr.models.KeepAliveMessage;
import com.example.bwr.models.Message;
import com.example.bwr.services.handlers.KeepAliveMessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeepAliveListener extends Listener {

  private final ObjectMapper objectMapper;
  private final KeepAliveMessageHandler keepAliveMessageHandler;

  public void listenTopic(Message message) {
    KeepAliveMessage keepAliveMessage = (KeepAliveMessage) message;

    keepAliveMessageHandler.handle(keepAliveMessage);
  }

  public boolean accept(MessageType type) {
    return MessageType.KEEP_ALIVE.equals(type);
  }

  public KeepAliveMessage convertToMessage(String message) {
    try {
      return objectMapper.readValue(message, KeepAliveMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to KeepAliveMessage");
    }
  }
}
