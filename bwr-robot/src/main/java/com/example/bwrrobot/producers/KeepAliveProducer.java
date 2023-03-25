package com.example.bwrrobot.producers;

import com.example.bwrrobot.models.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KeepAliveProducer extends Producer {

  @Value("${kafka.topic-name}")
  private String topicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KeepAliveProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
    super(objectMapper);
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendMessage(Message message) {
    kafkaTemplate.send(topicName, convertToString(message));
  }
}
