package com.example.bwr.configs.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AckTopicConfig {

  @Value("${kafka.ack.topic-name}")
  private String topicName;

  @Bean
  public NewTopic ackTopic() {
    return new NewTopic(topicName, 4, (short) 3);
  }
}
