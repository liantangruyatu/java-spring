package com.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 选题数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectionDTO {
    private Integer id;
    private StudentDTO student;
    private TopicDTO topic;
    private String status;
    private LocalDateTime createTime;
    private Integer selectionType;
    private String customTitle;
    private String customDescription;
    private String approvalComment;
    private LocalDateTime applyTime;
    private LocalDateTime approvalTime;
}
