package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细实体类
 */
@Data
@TableName("t_order_item")
public class OrderItem {
    @TableId(type = IdType.AUTO)
    private Long itemId;
    
    private Long orderId;
    
    private Long bookId;
    
    private Integer quantity;
    
    private BigDecimal unitPrice;
    
    private BigDecimal discount;
    
    private BigDecimal subtotal;
}

