package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 购物车实体类
 */
@Data
@TableName("t_cart")
public class Cart {
    @TableId(type = IdType.AUTO)
    private Long cartId;
    
    private Long userId;
    
    private Long bookId;
    
    private Integer quantity;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}

