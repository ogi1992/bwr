package com.example.bwr.repositories;

import com.example.bwr.entities.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Integer> {

}
