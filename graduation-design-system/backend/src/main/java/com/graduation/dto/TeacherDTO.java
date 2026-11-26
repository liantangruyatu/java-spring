package com.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 教师数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private Integer id;
    private String name;
    private String username;
    private String gender;
    private String title;
    private String email;
    private String phone;
    private String department;
    private String researchArea;
    private Integer maxStudents;
    private Integer currentCount;
}
