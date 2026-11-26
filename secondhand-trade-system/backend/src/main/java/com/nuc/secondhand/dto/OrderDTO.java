package com.nuc.secondhand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Integer orderId;
    private String orderNumber;
    private Integer buyerId;
    private String buyerName;
    private Integer sellerId;
    private String sellerName;
    private Integer productId;
    private String productName;
    private Double orderAmount;
    private String orderStatus;
    private Integer quantity;
    private String createTime;
    private String paymentTime;
    private String deliveryTime;
    private String receiptTime;
    private String completionTime;
    private Integer buyerRateScore;
    private String buyerRateComment;
    private Integer sellerRateScore;
    private String sellerRateComment;
    private String transactionAddress;
    private String remark;
}
