package com.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题目数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDTO {
    private Integer id;
    private String title;
    private String type;
    private String description;
    private Integer maxStudents;
    private Integer currentStudents;
    private TeacherDTO teacher;
}
