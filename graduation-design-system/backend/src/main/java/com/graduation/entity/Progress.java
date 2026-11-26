package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 毕设进度实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("progress")
public class Progress implements Serializable {
    
    @TableId(type = IdType.AUTO)
    private Integer progressId;
    
    private Integer selectionId;
    
    private Integer stage;
    
    private Integer status;
    
    private String submitFile;
    
    private LocalDateTime submitTime;
    
    private String teacherComment;
    
    private BigDecimal score;
    
    private LocalDateTime updateTime;
}
