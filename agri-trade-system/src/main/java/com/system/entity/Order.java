package com.system.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private String id;
    private Integer customerId;
    private Integer merchantId;
    private BigDecimal totalAmount;
    private Integer status; // 1待付款 2待发货 3已发货 4已完成 5已取消
    private String address;
    private String phone;
    private String receiver;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}