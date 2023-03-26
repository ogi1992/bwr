package com.example.bwraudit.entities;

import com.example.bwraudit.entities.converters.ActionTypeConverter;
import com.example.bwraudit.entities.converters.UserTypeConverter;
import com.example.bwraudit.enums.ActionType;
import com.example.bwraudit.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(catalog = "bwr-audit", name = "audit_log")
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    private Integer taskId;

    @Column(name = "action_type")
    @Convert(converter = ActionTypeConverter.class)
    private ActionType actionType;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "source_type")
    @Convert(converter = UserTypeConverter.class)
    private UserType sourceType;

    @Column(name = "target_id")
    private Integer targetId;

    @Column(name = "target_type")
    @Convert(converter = UserTypeConverter.class)
    private UserType targetType;
}
