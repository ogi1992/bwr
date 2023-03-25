package com.example.bwr.entities;

import com.example.bwr.entities.converters.StatusConverter;
import com.example.bwr.enums.Status;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private Integer robotId;

  private String route;

  @Convert(converter = StatusConverter.class)
  private Status status = Status.PENDING;
}
