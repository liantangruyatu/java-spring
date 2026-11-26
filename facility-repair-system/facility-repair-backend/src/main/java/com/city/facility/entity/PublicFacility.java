package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 公共设施实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "public_facility", indexes = {
    @Index(name = "idx_facility_code", columnList = "facility_code"),
    @Index(name = "idx_facility_type", columnList = "facility_type"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_location", columnList = "location")
})
public class PublicFacility {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50, name = "facility_code")
    private String facilityCode;
    
    @Column(nullable = false, name = "facility_type")
    @Enumerated(EnumType.STRING)
    private FacilityType facilityType;
    
    @Column(length = 200)
    private String location;
    
    @Column(precision = 10, scale = 8, name = "latitude")
    private Double latitude;
    
    @Column(precision = 11, scale = 8, name = "longitude")
    private Double longitude;
    
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private FacilityStatus status;
    
    @Column(name = "install_date")
    private LocalDate installDate;
    
    @Column(nullable = false, updatable = false, name = "create_time")
    private LocalDateTime createTime;
    
    @Column(nullable = false, name = "update_time")
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (status == null) {
            status = FacilityStatus.NORMAL;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
    
    public enum FacilityType {
        STREET_LIGHT,           // 路灯
        PARK_BENCH,             // 公园长椅
        PUBLIC_TOILET,          // 公共厕所
        FITNESS_EQUIPMENT       // 健身器材
    }
    
    public enum FacilityStatus {
        NORMAL,                 // 正常
        BROKEN,                 // 损坏
        REPAIRING,              // 维修中
        DISCARDED               // 已报废
    }
}
