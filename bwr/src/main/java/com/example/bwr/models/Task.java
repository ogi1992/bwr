package com.example.bwr.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

  private Integer id;
  @NotBlank(message = "Name must not be empty")
  private String name;
  @NotBlank(message = "Route must not be empty")
  private String route;
  @NotNull(message = "Robot id must not be empty")
  private Integer robotId;
}
