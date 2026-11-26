package com.system.service;

import com.system.entity.Cart;
import java.util.List;

public interface CartService {
    List<Cart> findByUserId(Integer userId);
    
    Cart findByUserIdAndProductId(Integer userId, Integer productId);
    
    Cart add(Cart cart);
    
    boolean updateQuantity(Integer id, Integer quantity);
    
    boolean deleteById(Integer id);
    
    boolean clearByUserId(Integer userId);
}