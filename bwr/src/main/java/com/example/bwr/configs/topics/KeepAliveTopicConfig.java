package com.example.bwr.configs.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeepAliveTopicConfig {

  @Value(value = "${kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value(value = "${kafka.topic-name}")
  private String topicName;

  @Bean
  public NewTopic keepAliveTopic() {
    return new NewTopic(topicName, 4, (short) 3);
  }
}
