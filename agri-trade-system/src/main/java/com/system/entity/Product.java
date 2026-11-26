package com.system.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Integer id;
    private Integer merchantId;
    private Integer categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String unit;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}