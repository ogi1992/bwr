package com.example.bwr.services;

import java.util.Date;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobScheduleCreator {

  public SimpleTrigger createSimpleTrigger(String triggerName, Date startTime, int misFireInstruction) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setName(triggerName);
    factoryBean.setStartTime(startTime);
    factoryBean.setRepeatCount(0);
    factoryBean.setMisfireInstruction(misFireInstruction);
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }

  public JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
      ApplicationContext context, String jobName, String jobGroup, Object data) {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    factoryBean.setDurability(isDurable);
    factoryBean.setApplicationContext(context);
    factoryBean.setName(jobName);
    factoryBean.setGroup(jobGroup);

    // Set job data map
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(jobName + jobGroup, jobClass.getName());
    jobDataMap.put("data", data);
    factoryBean.setJobDataMap(jobDataMap);
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }
}
