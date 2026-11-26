package com.nuc.secondhand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private Integer sellerId;

    @Column(nullable = false)
    private Integer categoryId;

    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "`condition`", nullable = false)
    private String condition;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime publishTime = LocalDateTime.now();

    private LocalDateTime saleStartTime;

    private LocalDateTime saleEndTime;

    @Column(nullable = false)
    private String productStatus = "在售";

    private Integer viewCount = 0;

    private String location;
}
