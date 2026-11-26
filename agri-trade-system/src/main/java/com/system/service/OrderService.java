package com.system.service;

import com.system.entity.Order;
import java.util.List;

public interface OrderService {
    Order findById(String id);
    
    List<Order> findByCustomerId(Integer customerId);
    
    List<Order> findByMerchantId(Integer merchantId);
    
    List<Order> findAll();
    
    List<Order> findByStatus(Integer status);
    
    Order createOrder(Integer customerId, Integer merchantId, String address, String phone, String receiver, List<Integer> productIds, List<Integer> quantities);
    
    boolean updateStatus(String orderId, Integer status);
    
    boolean cancelOrder(String orderId);
    
    boolean confirmReceipt(String orderId);
}