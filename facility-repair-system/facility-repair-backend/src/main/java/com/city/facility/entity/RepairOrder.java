package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 报修单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "repair_order", indexes = {
    @Index(name = "idx_repair_code", columnList = "repair_code"),
    @Index(name = "idx_facility_id", columnList = "facility_id"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_worker_id", columnList = "worker_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_submit_time", columnList = "submit_time")
})
public class RepairOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50, name = "repair_code")
    private String repairCode;
    
    @Column(nullable = false, name = "facility_id")
    private Long facilityId;
    
    @Column(nullable = false, name = "user_id")
    private Long userId;
    
    @Column(name = "worker_id")
    private Long workerId;
    
    @Column(nullable = false, columnDefinition = "TEXT", name = "problem_description")
    private String problemDescription;
    
    @Column(columnDefinition = "VARCHAR(1000)")
    private String images;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RepairStatus status;
    
    @Column(nullable = false, updatable = false, name = "submit_time")
    private LocalDateTime submitTime;
    
    @Column(name = "accept_time")
    private LocalDateTime acceptTime;
    
    @Column(name = "complete_time")
    private LocalDateTime completeTime;
    
    @Column(columnDefinition = "TEXT")
    private String feedback;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    @Column(nullable = false)
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        submitTime = LocalDateTime.now();
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (status == null) {
            status = RepairStatus.SUBMITTED;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
    
    public enum RepairStatus {
        SUBMITTED,      // 已提交
        ACCEPTED,       // 已接受
        PROCESSING,     // 维修中
        COMPLETED       // 已完成
    }
}
