package com.graduation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private Integer id;
    private String name;
    private String username;
    private String gender;
    @JsonProperty("class")
    private String clazz;
    private String major;
    private String email;
    private String phone;
    private String studentNo;
}
