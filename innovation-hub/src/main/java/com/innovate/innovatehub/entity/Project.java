package com.innovate.innovatehub.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 项目实体类
 */
@Data
public class Project {
    private Integer projectId;
    private String projectName;
    private String description;
    private Integer teacherId;
    private Integer studentId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 关联信息（用于查询展示）
    private String teacherName;
    private String studentName;
}
