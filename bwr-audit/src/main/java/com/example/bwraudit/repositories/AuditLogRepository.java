package com.example.bwraudit.repositories;

import com.example.bwraudit.entities.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Integer> {

}
