package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 统计实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "statistics", indexes = {
    @Index(name = "idx_stat_date", columnList = "stat_date")
})
public class Statistics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 30)
    private String facilityType;
    
    @Column(nullable = false)
    private Integer totalCount;
    
    @Column(nullable = false)
    private Integer brokenCount;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal faultRate;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal avgRepairTime;
    
    @Column(name = "stat_date")
    private LocalDate statDate;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    @Column(nullable = false)
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (totalCount == null) {
            totalCount = 0;
        }
        if (brokenCount == null) {
            brokenCount = 0;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
