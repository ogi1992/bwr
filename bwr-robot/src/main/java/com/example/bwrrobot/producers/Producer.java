package com.example.bwrrobot.producers;

import com.example.bwrrobot.models.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Producer {

  private final ObjectMapper objectMapper;

  public abstract void sendMessage(Message message, Integer robotId);

  public String convertToString(Message message) {
    try {
      return objectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Can't convert message to String");
    }
  }
}
