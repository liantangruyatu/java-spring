package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报修单响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairOrderResponse {
    private Long id;
    private String repairCode;
    private Long facilityId;
    private String facilityCode;
    private String facilityType;
    private Long userId;
    private String citizenName;
    private Long workerId;
    private String workerName;
    private String problemDescription;
    private String images;
    private String status;
    private String submitTime;
    private String acceptTime;
    private String completeTime;
    private String feedback;
    private Long repairHours;  // 维修耗时(小时)
}
