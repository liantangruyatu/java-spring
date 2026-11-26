package com.system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sort;
    private LocalDateTime createdAt;
}