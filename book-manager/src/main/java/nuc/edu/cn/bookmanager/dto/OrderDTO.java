package nuc.edu.cn.bookmanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单DTO（包含订单明细）
 */
@Data
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private String orderNo;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private Integer status;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddr;
    private LocalDateTime payTime;
    private LocalDateTime deliverTime;
    private LocalDateTime finishTime;
    private List<OrderItemDTO> items;
}

