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
 * 学生选题实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("selections")
public class Selection implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Integer selectionId;
    
    private Integer studentId;
    
    private Integer topicId;
    
    private Integer selectionType;
    
    private String customTitle;
    
    private String customDescription;
    
    private Integer teacherId;
    
    private Integer status;
    
    private LocalDateTime applyTime;
    
    private LocalDateTime approvalTime;
    
    private String approvalComment;
    
    private LocalDateTime updateTime;
}
