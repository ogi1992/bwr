package com.example.bwr.utils;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.RobotState;
import com.example.bwr.enums.Status;
import com.example.bwr.models.Task;
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

  public static TaskEntity buildTaskEntity(Status status) {
    return TaskEntity.builder()
        .id(TASK_ID)
        .robotId(ROBOT_ID)
        .status(status)
        .build();
  }

  public static RobotEntity buildRobotEntity(RobotState robotState) {
    return new RobotEntity(ROBOT_ID, "test", robotState);
  }

  public static Task buildTask() {
    return Task.builder()
        .name("test task")
        .route("test route")
        .robotId(ROBOT_ID)
        .build();
  }
}
