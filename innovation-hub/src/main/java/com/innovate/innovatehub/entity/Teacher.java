package com.innovate.innovatehub.entity;

import lombok.Data;

/**
 * 教师实体类
 */
@Data
public class Teacher {
    private Integer teacherId;
    private String username;
    private String password;
    private String name;
    private String college;
    private String title;
    private String phone;
    private String email;
}
