package com.example.bwraudit.configs.topics;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class AuditTopicConfig {

  @Value(value = "${kafka.bootstrap-servers}")
  private String bootstrapServers;
  @Value(value = "${kafka.audit.topic-name}")
  private String topicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic auditTopic() {
    return new NewTopic(topicName, 4, (short) 3);
  }
}
