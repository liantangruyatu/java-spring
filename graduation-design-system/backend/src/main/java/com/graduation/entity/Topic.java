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
 * 毕设题目实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("topics")
public class Topic implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Integer topicId;
    
    private Integer teacherId;
    
    private String title;
    
    private String description;
    
    private Integer type;
    
    private Integer difficulty;
    
    private String requiredSkills;
    
    private Integer maxStudents;
    
    private Integer selectedCount;
    
    private Integer status;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
