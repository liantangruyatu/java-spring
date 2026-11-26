package com.system.controller;

import com.system.entity.Order;
import com.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // 获取订单详情
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        Order order = orderService.findById(id);
        if (order != null) {
            result.put("success", true);
            result.put("data", order);
        } else {
            result.put("success", false);
            result.put("message", "订单不存在");
        }
        return ResponseEntity.ok(result);
    }
    
    // 获取用户订单
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Map<String, Object>> getOrdersByCustomerId(@PathVariable Integer customerId) {
        Map<String, Object> result = new HashMap<>();
        List<Order> orders = orderService.findByCustomerId(customerId);
        result.put("success", true);
        result.put("data", orders);
        return ResponseEntity.ok(result);
    }
    
    // 获取商家订单
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<Map<String, Object>> getOrdersByMerchantId(@PathVariable Integer merchantId) {
        Map<String, Object> result = new HashMap<>();
        List<Order> orders = orderService.findByMerchantId(merchantId);
        result.put("success", true);
        result.put("data", orders);
        return ResponseEntity.ok(result);
    }
    
    // 获取所有订单
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        Map<String, Object> result = new HashMap<>();
        List<Order> orders = orderService.findAll();
        result.put("success", true);
        result.put("data", orders);
        return ResponseEntity.ok(result);
    }
    
    // 根据状态获取订单
    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getOrdersByStatus(@PathVariable Integer status) {
        Map<String, Object> result = new HashMap<>();
        List<Order> orders = orderService.findByStatus(status);
        result.put("success", true);
        result.put("data", orders);
        return ResponseEntity.ok(result);
    }
    
    // 创建订单
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestParam Integer customerId,
                                                           @RequestParam Integer merchantId,
                                                           @RequestParam String address,
                                                           @RequestParam String phone,
                                                           @RequestParam String receiver,
                                                           @RequestBody Map<String, Object> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            List<Integer> productIds = (List<Integer>) request.get("productIds");
            @SuppressWarnings("unchecked")
            List<Integer> quantities = (List<Integer>) request.get("quantities");
            
            Order order = orderService.createOrder(customerId, merchantId, address, phone, receiver, productIds, quantities);
            result.put("success", true);
            result.put("message", "订单创建成功");
            result.put("data", order);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
    
    // 更新订单状态
    @PutMapping("/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@RequestParam String orderId, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        boolean success = orderService.updateStatus(orderId, status);
        if (success) {
            result.put("success", true);
            result.put("message", "订单状态更新成功");
        } else {
            result.put("success", false);
            result.put("message", "订单状态更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 取消订单
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable String orderId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = orderService.cancelOrder(orderId);
        if (success) {
            result.put("success", true);
            result.put("message", "订单取消成功");
        } else {
            result.put("success", false);
            result.put("message", "订单取消失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 确认收货
    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<Map<String, Object>> confirmReceipt(@PathVariable String orderId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = orderService.confirmReceipt(orderId);
        if (success) {
            result.put("success", true);
            result.put("message", "确认收货成功");
        } else {
            result.put("success", false);
            result.put("message", "确认收货失败");
        }
        return ResponseEntity.ok(result);
    }
}