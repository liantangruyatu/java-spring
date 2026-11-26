package com.nuc.secondhand.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private Integer buyerId;

    @Column(nullable = false)
    private Integer sellerId;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Double orderAmount;

    @Column(nullable = false)
    private String orderStatus = "待支付";

    private Integer quantity = 1;

    @Column(nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    private LocalDateTime paymentTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime receiptTime;

    private LocalDateTime completionTime;

    private Integer buyerRateScore;

    @Column(columnDefinition = "TEXT")
    private String buyerRateComment;

    private Integer sellerRateScore;

    @Column(columnDefinition = "TEXT")
    private String sellerRateComment;

    private String transactionAddress;

    private String remark;
}
