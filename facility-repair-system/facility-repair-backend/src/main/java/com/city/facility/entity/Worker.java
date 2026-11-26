package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 维修工实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "worker", indexes = {
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_pending_count", columnList = "pending_count")
})
public class Worker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, name = "user_id")
    private Long userId;
    
    @Column(length = 100, name = "specialization")
    private String specialization;
    
    @Column(length = 20, name = "phone")
    private String phone;
    
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private WorkerStatus status;
    
    @Column(nullable = false, name = "pending_count")
    private Integer pendingCount;
    
    @Column(nullable = false, updatable = false, name = "create_time")
    private LocalDateTime createTime;
    
    @Column(nullable = false, name = "update_time")
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (status == null) {
            status = WorkerStatus.AVAILABLE;
        }
        if (pendingCount == null) {
            pendingCount = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
    
    public enum WorkerStatus {
        AVAILABLE,      // 可用(无任务)
        BUSY,           // 忙碌(有任务)
        OFFLINE         // 离线
    }
}
