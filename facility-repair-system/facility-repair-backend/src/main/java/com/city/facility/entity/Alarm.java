package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 预警实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "alarm", indexes = {
    @Index(name = "idx_alarm_type", columnList = "alarm_type"),
    @Index(name = "idx_is_read", columnList = "is_read"),
    @Index(name = "idx_create_time", columnList = "create_time")
})
public class Alarm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long repairOrderId;
    
    @Column(name = "alarm_type", nullable = false, length = 50)
    private String alarmType;
    
    @Column(columnDefinition = "TEXT")
    private String alarmContent;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlarmLevel alarmLevel;
    
    @Column(name = "is_read", nullable = false)
    private Boolean isRead;
    
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        if (isRead == null) {
            isRead = false;
        }
        if (alarmLevel == null) {
            alarmLevel = AlarmLevel.MEDIUM;
        }
    }
    
    public enum AlarmLevel {
        LOW,            // 低
        MEDIUM,         // 中
        HIGH            // 高
    }
}
