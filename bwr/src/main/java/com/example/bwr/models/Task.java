package com.example.bwr.models;

import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

  private Integer id;
  @NotBlank(message = "Name must not be empty")
  private String name;
  @NotBlank(message = "Route must not be empty")
  private String route;
  @NotNull(message = "Robot id must not be empty")
  private Integer robotId;
  private Status status;

  public static Task buildTask(TaskEntity taskEntity) {
    return Task.builder()
        .id(taskEntity.getId())
        .name(taskEntity.getName())
        .route(taskEntity.getRoute())
        .robotId(taskEntity.getRobotId())
        .status(taskEntity.getStatus())
        .build();
  }
}
