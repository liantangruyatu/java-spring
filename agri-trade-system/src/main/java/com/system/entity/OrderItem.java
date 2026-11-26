package com.system.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Integer id;
    private String orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
}