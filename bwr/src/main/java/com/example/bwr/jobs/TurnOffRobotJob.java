package com.example.bwr.jobs;

import com.example.bwr.enums.RobotState;
import com.example.bwr.services.RobotService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TurnOffRobotJob extends QuartzJobBean {

  private final RobotService robotService;

  @Override
  protected void executeInternal(JobExecutionContext context) {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Integer robotId = (Integer) jobDataMap.get("data");

    robotService.updateState(robotId, RobotState.OFF);
  }
}
