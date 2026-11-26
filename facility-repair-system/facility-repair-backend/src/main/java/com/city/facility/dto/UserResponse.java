package com.city.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String phone;
    private String realName;
    private String userType;
    private String status;
    private String createTime;
    private String updateTime;
}
