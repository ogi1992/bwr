package com.example.bwr.producers;

import com.example.bwr.models.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BWRProducer extends Producer {

  @Value("${kafka.bwr-topic-name}")
  private String topicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public BWRProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
    super(objectMapper);
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendMessage(Message message) {
    kafkaTemplate.send(topicName, convertToString(message));
  }
}
