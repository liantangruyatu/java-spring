package com.system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String realName;
    private String role; // admin, merchant, customer
    private String avatar;
    private String address;
    private Integer status; // 1正常 0禁用
    private LocalDateTime createdAt;
}