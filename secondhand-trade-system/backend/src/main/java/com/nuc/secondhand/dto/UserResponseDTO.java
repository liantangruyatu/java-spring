package com.nuc.secondhand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String realName;
    private String gender;
    private String department;
    private String phoneNumber;
    private String email;
    private String address;
    private String userType;
    private String avatarUrl;
    private Integer creditScore;
    private String createTime;
    private String lastLoginTime;
}
