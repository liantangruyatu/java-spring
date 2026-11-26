package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 报修单创建请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairOrderCreateRequest {
    
    @NotNull(message = "设施ID不能为空")
    private Long facilityId;
    
    @NotBlank(message = "问题描述不能为空")
    private String problemDescription;
    
    private String images;  // 图片URL列表,逗号分隔
}
