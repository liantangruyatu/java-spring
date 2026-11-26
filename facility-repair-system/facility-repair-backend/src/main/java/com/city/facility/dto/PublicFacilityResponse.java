package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 公共设施响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicFacilityResponse {
    
    private Long id;
    private String facilityCode;
    private String facilityType;
    private String location;
    private Double latitude;
    private Double longitude;
    private String status;
    private String installDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
