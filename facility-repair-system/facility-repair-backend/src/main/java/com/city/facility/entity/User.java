package com.city.facility.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_phone", columnList = "phone"),
    @Index(name = "idx_user_type", columnList = "user_type")
})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String phone;
    
    @Column(nullable = false, length = 100)
    private String password;
    
    @Column(length = 50)
    private String realName;
    
    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    @Column(nullable = false)
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
        if (status == null) {
            status = UserStatus.ACTIVE;
        }
        if (userType == null) {
            userType = UserType.CITIZEN;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
    
    public enum UserType {
        CITIZEN,    // 普通市民
        WORKER,     // 维修工
        ADMIN       // 管理员
    }
    
    public enum UserStatus {
        ACTIVE,     // 激活
        INACTIVE,   // 停用
        BANNED      // 封禁
    }
}
