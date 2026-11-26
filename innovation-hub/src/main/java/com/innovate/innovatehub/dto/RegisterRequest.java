package com.innovate.innovatehub.dto;

import lombok.Data;

/**
 * 注册请求
 */
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String userType; // "teacher" 或 "student"
    
    // 教师专属字段
    private String college;
    private String title;
    
    // 学生专属字段
    private String major;
    private String className;
    
    // 公共字段
    private String phone;
    private String email;
}
