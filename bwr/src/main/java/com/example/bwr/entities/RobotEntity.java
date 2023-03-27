package com.example.bwr.entities;

import com.example.bwr.entities.converters.RobotStateConverter;
import com.example.bwr.enums.RobotState;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(catalog = "bwr", name = "robot")
@NoArgsConstructor
@AllArgsConstructor
public class RobotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Convert(converter = RobotStateConverter.class)
  private RobotState state = RobotState.OFF;

}
