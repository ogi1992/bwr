package com.example.bwr.utils;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.RobotState;
import com.example.bwr.models.TaskMessage;

public class TestUtils {

  private TestUtils(){

  }

  public static final Integer TASK_ID = 1;
  public static final Integer USER_ID = 1;
  public static final Integer ROBOT_ID = 1;

  public static TaskEntity buildTaskEntity(TaskMessage taskMessage) {
    return TaskEntity.builder()
        .id(taskMessage.getId())
        .robotId(ROBOT_ID)
        .build();
  }

  public static RobotEntity buildRobotEntity() {
    return new RobotEntity(ROBOT_ID, "test", RobotState.OFF);
  }

}
