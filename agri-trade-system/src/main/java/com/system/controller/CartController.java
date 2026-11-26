package com.system.controller;

import com.system.entity.Cart;
import com.system.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    // 获取用户购物车
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getCartByUserId(@PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<>();
        List<Cart> cartItems = cartService.findByUserId(userId);
        result.put("success", true);
        result.put("data", cartItems);
        return ResponseEntity.ok(result);
    }
    
    // 添加商品到购物车
    @PostMapping
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Cart cart) {
        Map<String, Object> result = new HashMap<>();
        Cart existingCart = cartService.findByUserIdAndProductId(cart.getUserId(), cart.getProductId());
        if (existingCart != null) {
            // 如果购物车中已存在该商品，则更新数量
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
            cartService.updateQuantity(existingCart.getId(), existingCart.getQuantity());
            result.put("success", true);
            result.put("message", "购物车商品数量已更新");
            result.put("data", existingCart);
        } else {
            // 否则添加新商品到购物车
            Cart newCart = cartService.add(cart);
            result.put("success", true);
            result.put("message", "商品已添加到购物车");
            result.put("data", newCart);
        }
        return ResponseEntity.ok(result);
    }
    
    // 更新购物车商品数量
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCartQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        Map<String, Object> result = new HashMap<>();
        boolean success = cartService.updateQuantity(id, quantity);
        if (success) {
            result.put("success", true);
            result.put("message", "购物车商品数量已更新");
        } else {
            result.put("success", false);
            result.put("message", "更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 从购物车删除商品
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> removeFromCart(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = cartService.deleteById(id);
        if (success) {
            result.put("success", true);
            result.put("message", "商品已从购物车删除");
        } else {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 清空购物车
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> clearCart(@PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = cartService.clearByUserId(userId);
        if (success) {
            result.put("success", true);
            result.put("message", "购物车已清空");
        } else {
            result.put("success", false);
            result.put("message", "清空失败");
        }
        return ResponseEntity.ok(result);
    }
}