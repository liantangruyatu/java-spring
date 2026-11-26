package com.innovate.innovatehub.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 选题记录实体类
 */
@Data
public class Selection {
    private Integer selectionId;
    private Integer studentId;
    private Integer topicId;
    private LocalDateTime selectedAt;
    private String status;
    
    // 关联信息（用于查询展示）
    private String studentName;
    private String topicTitle;
    private String teacherName;
}
