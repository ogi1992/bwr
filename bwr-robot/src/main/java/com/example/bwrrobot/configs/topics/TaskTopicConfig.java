package com.example.bwrrobot.configs.topics;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class TaskTopicConfig {

  @Value(value = "${kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value(value = "${kafka.topic-name}")
  private String topicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic topic() {
    return new NewTopic(topicName, 10, (short) 3);
  }
}
