package com.nuc.secondhand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Integer productId;
    private Integer sellerId;
    private String sellerName;
    private Integer categoryId;
    private String categoryName;
    private String productName;
    private String description;
    private Double price;
    private String condition;
    private String imageUrl;
    private String publishTime;
    private String saleStartTime;
    private String saleEndTime;
    private String productStatus;
    private Integer viewCount;
    private String location;
}
