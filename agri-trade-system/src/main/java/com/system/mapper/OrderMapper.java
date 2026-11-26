package com.system.mapper;

import com.system.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    Order findById(@Param("id") String id);
    
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);
    
    List<Order> findByMerchantId(@Param("merchantId") Integer merchantId);
    
    List<Order> findAll();
    
    int insert(Order order);
    
    int update(Order order);
    
    int updateStatus(@Param("id") String id, @Param("status") Integer status);
    
    List<Order> findByStatus(@Param("status") Integer status);
}