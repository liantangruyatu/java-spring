package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计数据响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsResponse {
    private String facilityType;
    private Integer totalCount;
    private Integer brokenCount;
    private Integer repairingCount;
    private Integer normalCount;
    private Double faultRate;
    private Double avgRepairTime;
    private String statDate;
}
