package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("students")
public class Student implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Integer studentId;
    
    private Integer userId;
    
    private String realName;
    
    private String studentNo;
    
    private String major;
    
    private String className;
    
    private String phone;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
