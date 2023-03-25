package com.example.bwr.services.handlers;

import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.jobs.TurnOffRobotJob;
import com.example.bwr.models.KeepAliveMessage;
import com.example.bwr.services.JobScheduleCreator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeepAliveMessageHandler {

  private final SchedulerFactoryBean schedulerFactoryBean;
  private final JobScheduleCreator jobScheduleCreator;
  private final ApplicationContext applicationContext;

  public void handle(KeepAliveMessage keepAliveMessage) {
    createScheduleJob(keepAliveMessage);
  }

  private void createScheduleJob(KeepAliveMessage keepAliveMessage) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();

    String jobIdentity = getIdentity(keepAliveMessage);
    String jobGroup = getJobGroup(keepAliveMessage);

    JobDetail job = JobBuilder.newJob(TurnOffRobotJob.class)
        .withIdentity(jobIdentity, jobGroup)
        .build();

    try {
      if (!scheduler.checkExists(job.getKey())) {
        job = jobScheduleCreator.createJob(TurnOffRobotJob.class, false, applicationContext,
            jobIdentity, jobGroup, keepAliveMessage.getSourceId());

        Date startDateTime = Date.from(LocalDateTime.now().plusSeconds(10L).atZone(ZoneId.systemDefault()).toInstant());
        Trigger trigger = jobScheduleCreator.createSimpleTrigger(jobIdentity, startDateTime,
            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

        scheduler.scheduleJob(job, trigger);
      } else {
        Date startDateTime = Date.from(LocalDateTime.now().plusSeconds(10L).atZone(ZoneId.systemDefault()).toInstant());

        Trigger newTrigger = jobScheduleCreator.createSimpleTrigger(
            jobIdentity,
            startDateTime,
            SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

        schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobIdentity), newTrigger);
        log.info(">>>>> jobName = [" + jobIdentity + "]" + " updated and scheduled.");
      }
    } catch (SchedulerException e) {
      log.error("Reschedule job failed!", e);
      throw ExceptionSuppliers.scheduleJobException.get();
    }

  }

  private String getIdentity(KeepAliveMessage keepAliveMessage) {
    return keepAliveMessage.getSourceId() + "-" + TurnOffRobotJob.class.getSimpleName();
  }

  public String getJobGroup(KeepAliveMessage keepAliveMessage) {
    return keepAliveMessage.getSourceId() + "-" + TurnOffRobotJob.class.getSimpleName() + "-group";
  }
}
