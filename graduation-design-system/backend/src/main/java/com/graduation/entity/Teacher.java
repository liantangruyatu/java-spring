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
 * 教师实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("teachers")
public class Teacher implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Integer teacherId;
    
    private Integer userId;
    
    private String realName;
    
    private String phone;
    
    private String department;
    
    private String researchArea;
    
    private Integer maxStudents;
    
    private Integer currentCount;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
