package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long orderId;
    
    private Long userId;
    
    private String orderNo;
    
    private LocalDateTime orderDate;
    
    private BigDecimal totalAmount;
    
    /**
     * 0-待支付 1-已支付 2-已发货 3-已完成 4-已取消
     */
    private Integer status;
    
    private String receiverName;
    
    private String receiverPhone;
    
    private String receiverAddr;
    
    private LocalDateTime payTime;
    
    private LocalDateTime deliverTime;
    
    private LocalDateTime finishTime;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}

