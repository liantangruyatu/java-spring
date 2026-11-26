package com.system.service.impl;

import com.system.entity.Cart;
import com.system.mapper.CartMapper;
import com.system.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartMapper cartMapper;
    
    @Override
    public List<Cart> findByUserId(Integer userId) {
        return cartMapper.findByUserId(userId);
    }
    
    @Override
    public Cart findByUserIdAndProductId(Integer userId, Integer productId) {
        return cartMapper.findByUserIdAndProductId(userId, productId);
    }
    
    @Override
    public Cart add(Cart cart) {
        cartMapper.insert(cart);
        return cart;
    }
    
    @Override
    public boolean updateQuantity(Integer id, Integer quantity) {
        return cartMapper.updateQuantity(id, quantity) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return cartMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean clearByUserId(Integer userId) {
        return cartMapper.clearByUserId(userId) > 0;
    }
}