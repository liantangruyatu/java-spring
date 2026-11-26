package com.system.service.impl;

import com.system.entity.*;
import com.system.mapper.*;
import com.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private CartMapper cartMapper;
    
    @Override
    public Order findById(String id) {
        return orderMapper.findById(id);
    }
    
    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        return orderMapper.findByCustomerId(customerId);
    }
    
    @Override
    public List<Order> findByMerchantId(Integer merchantId) {
        return orderMapper.findByMerchantId(merchantId);
    }
    
    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }
    
    @Override
    public List<Order> findByStatus(Integer status) {
        return orderMapper.findByStatus(status);
    }
    
    @Override
    @Transactional
    public Order createOrder(Integer customerId, Integer merchantId, String address, String phone, String receiver, List<Integer> productIds, List<Integer> quantities) {
        if (productIds == null || quantities == null || productIds.size() != quantities.size()) {
            throw new IllegalArgumentException("商品ID和数量列表必须存在且长度相等");
        }
        
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            
            Product product = productMapper.findById(productId);
            if (product == null) {
                throw new RuntimeException("商品不存在: " + productId);
            }
            
            if (product.getStock() < quantity) {
                throw new RuntimeException("商品库存不足: " + product.getName());
            }
            
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(quantity)));
        }
        
        // 创建订单
        String orderId = "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId(customerId);
        order.setMerchantId(merchantId);
        order.setTotalAmount(totalAmount);
        order.setStatus(1); // 待付款
        order.setAddress(address);
        order.setPhone(phone);
        order.setReceiver(receiver);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        orderMapper.insert(order);
        
        // 创建订单项
        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = productIds.get(i);
            Integer quantity = quantities.get(i);
            
            Product product = productMapper.findById(productId);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            
            orderItemMapper.insert(orderItem);
            
            // 更新库存
            productMapper.updateStock(productId, product.getStock() - quantity);
        }
        
        return order;
    }
    
    @Override
    public boolean updateStatus(String orderId, Integer status) {
        return orderMapper.updateStatus(orderId, status) > 0;
    }
    
    @Override
    public boolean cancelOrder(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null || order.getStatus() > 2) { // 已发货或已完成的订单不能取消
            return false;
        }
        
        // 如果订单已付款但未发货，需要恢复库存
        if (order.getStatus() >= 2) {
            List<OrderItem> items = orderItemMapper.findByOrderId(orderId);
            for (OrderItem item : items) {
                Product product = productMapper.findById(item.getProductId());
                if (product != null) {
                    productMapper.updateStock(item.getProductId(), product.getStock() + item.getQuantity());
                }
            }
        }
        
        return orderMapper.updateStatus(orderId, 5) > 0; // 设置为已取消
    }
    
    @Override
    public boolean confirmReceipt(String orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null || order.getStatus() != 3) { // 必须是已发货状态
            return false;
        }
        
        return orderMapper.updateStatus(orderId, 4) > 0; // 设置为已完成
    }
}