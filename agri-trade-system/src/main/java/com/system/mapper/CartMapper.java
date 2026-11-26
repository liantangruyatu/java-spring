package com.system.mapper;

import com.system.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CartMapper {
    List<Cart> findByUserId(@Param("userId") Integer userId);
    
    Cart findByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    
    int insert(Cart cart);
    
    int updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);
    
    int deleteById(@Param("id") Integer id);
    
    int deleteByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    
    int clearByUserId(@Param("userId") Integer userId);
}