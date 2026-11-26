package nuc.edu.cn.bookmanager.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细DTO（包含图书信息）
 */
@Data
public class OrderItemDTO {
    private Long itemId;
    private Long orderId;
    private Long bookId;
    private String bookName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal subtotal;
}

