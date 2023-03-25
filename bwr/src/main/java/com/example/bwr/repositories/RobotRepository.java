package com.example.bwr.repositories;

import com.example.bwr.entities.RobotEntity;
import com.example.bwr.enums.RobotState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RobotRepository extends JpaRepository<RobotEntity, Integer> {

  @Modifying
  @Query(value = "UPDATE RobotEntity r SET r.state = :robotState WHERE r.id = :robotId")
  void updateRobotStateById(@Param("robotId") Integer robotId, @Param("robotState") RobotState robotState);
}
