package com.system.mapper;

import com.system.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> findByOrderId(@Param("orderId") String orderId);
    
    int insert(OrderItem orderItem);
    
    int insertBatch(@Param("orderItems") List<OrderItem> orderItems);
    
    int deleteByOrderId(@Param("orderId") String orderId);
}