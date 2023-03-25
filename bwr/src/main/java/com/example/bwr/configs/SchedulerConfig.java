package com.example.bwr.configs;

import java.util.Map;
import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@ConfigurationProperties(prefix = "quartz")
public class SchedulerConfig {

  private final ApplicationContext applicationContext;
  private Map<String, String> properties;

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public SchedulerConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {

    SchedulerJobFactory jobFactory = new SchedulerJobFactory();
    jobFactory.setApplicationContext(applicationContext);

    Properties properties = new Properties();
    properties.putAll(this.properties);

    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(true);
    factory.setQuartzProperties(properties);
    factory.setJobFactory(jobFactory);
    return factory;
  }
}
