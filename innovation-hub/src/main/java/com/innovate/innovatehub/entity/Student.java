package com.innovate.innovatehub.entity;

import lombok.Data;

/**
 * 学生实体类
 */
@Data
public class Student {
    private Integer studentId;
    private String username;
    private String password;
    private String name;
    private String major;
    private String className;
    private String phone;
    private String email;
}
