package com.innovate.innovatehub.dto;

import lombok.Data;

/**
 * 登录请求
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
    private String userType; // "teacher" 或 "student"
}
