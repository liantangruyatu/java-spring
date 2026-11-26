package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 报修单处理请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairOrderProcessRequest {
    
    @NotNull(message = "报修单ID不能为空")
    private Long repairOrderId;
    
    private String status;  // accepted/processing/completed
    
    private String feedback;  // 维修反馈
}
