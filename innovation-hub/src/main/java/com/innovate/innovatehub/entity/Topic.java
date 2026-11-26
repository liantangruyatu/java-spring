package com.innovate.innovatehub.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 题目实体类
 */
@Data
public class Topic {
    private Integer topicId;
    private String title;
    private String description;
    private Integer teacherId;
    private LocalDateTime createdAt;
    
    // 关联信息（用于查询展示）
    private String teacherName;
}
