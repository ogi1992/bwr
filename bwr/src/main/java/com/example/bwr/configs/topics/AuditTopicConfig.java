package com.example.bwr.configs.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AuditTopicConfig {

  @Value(value = "${kafka.audit.topic-name}")
  private String topicName;

  @Bean
  public NewTopic auditTopic() {
    return new NewTopic(topicName, 4, (short) 3);
  }
}
