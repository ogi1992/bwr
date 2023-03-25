package com.example.bwr.producers;

import com.example.bwr.models.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer extends Producer {

  @Value("${kafka.topic-name}")
  private String topicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public TaskProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
    super(objectMapper);
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendMessage(Message message) {
    kafkaTemplate.send(topicName, convertToString(message));
  }
}
