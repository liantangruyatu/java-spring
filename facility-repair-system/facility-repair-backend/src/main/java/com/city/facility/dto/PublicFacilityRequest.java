package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公共设施创建/更新请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicFacilityRequest {
    
    @NotBlank(message = "设施编号不能为空")
    private String facilityCode;
    
    @NotNull(message = "设施类型不能为空")
    private String facilityType;  // street_light/park_bench/public_toilet/fitness_equipment
    
    @NotBlank(message = "位置不能为空")
    private String location;
    
    private Double latitude;
    
    private Double longitude;
    
    private String status;  // normal/broken/repairing/discarded
    
    private String installDate;
}
