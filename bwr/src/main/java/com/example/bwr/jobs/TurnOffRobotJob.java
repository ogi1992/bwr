package com.example.bwr.jobs;

import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.RobotState;
import com.example.bwr.enums.UserType;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.services.AuditLogService;
import com.example.bwr.services.RobotService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TurnOffRobotJob extends QuartzJobBean {

  private final RobotService robotService;
  private final AuditLogService auditLogService;

  @Override
  protected void executeInternal(JobExecutionContext context) {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Integer robotId = (Integer) jobDataMap.get("data");

    robotService.updateState(robotId, RobotState.OFF);

    AuditLogMessage auditLogMessage = AuditLogMessage.buildAuditLogMessage(LocalDateTime.now(), null,
        ActionType.TURN_OFF_ROBOT, null, UserType.SERVER, robotId, UserType.ROBOT);

    auditLogService.logEvent(auditLogMessage);
  }
}
