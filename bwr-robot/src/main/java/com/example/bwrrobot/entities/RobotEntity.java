package com.example.bwrrobot.entities;

import com.example.bwrrobot.entities.converters.RobotStateConverter;
import com.example.bwrrobot.enums.RobotState;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(catalog = "bwr", name = "robot")
public class RobotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Convert(converter = RobotStateConverter.class)
  private RobotState state = RobotState.ON;
}
