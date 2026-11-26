package nuc.edu.cn.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nuc.edu.cn.bookmanager.entity.*;
import nuc.edu.cn.bookmanager.dto.OrderDTO;
import nuc.edu.cn.bookmanager.mapper.*;
import nuc.edu.cn.bookmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    @Autowired
    private CartMapper cartMapper;
    
    @Autowired
    private BookMapper bookMapper;
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Override
    @Transactional
    public String createOrder(Long userId, String receiverName, String receiverPhone, String receiverAddr) {
        // 1. 查询购物车
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.eq("user_id", userId);
        List<Cart> carts = cartMapper.selectList(cartWrapper);
        
        if (carts.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }
        
        // 2. 计算总金额并检查库存
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cart : carts) {
            Book book = bookMapper.selectById(cart.getBookId());
            if (book == null || book.getStock() < cart.getQuantity()) {
                throw new RuntimeException("商品库存不足：" + book.getBookName());
            }
            totalAmount = totalAmount.add(book.getPrice().multiply(new BigDecimal(cart.getQuantity())));
        }
        
        // 3. 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo("T" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setTotalAmount(totalAmount);
        order.setStatus(0); // 待支付
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setReceiverAddr(receiverAddr);
        this.save(order);
        
        // 4. 创建订单明细并扣减库存
        for (Cart cart : carts) {
            Book book = bookMapper.selectById(cart.getBookId());
            
            OrderItem item = new OrderItem();
            item.setOrderId(order.getOrderId());
            item.setBookId(cart.getBookId());
            item.setQuantity(cart.getQuantity());
            item.setUnitPrice(book.getPrice());
            item.setDiscount(new BigDecimal("1.0"));
            item.setSubtotal(book.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            orderItemMapper.insert(item);
            
            // 扣减库存
            book.setStock(book.getStock() - cart.getQuantity());
            book.setSalesVolume(book.getSalesVolume() + cart.getQuantity());
            bookMapper.updateById(book);
            
            // 删除购物车
            cartMapper.deleteById(cart.getCartId());
        }
        
        return order.getOrderNo();
    }
    
    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        return baseMapper.selectOrdersByUserId(userId);
    }
    
    @Override
    public OrderDTO getOrderDetail(Long orderId) {
        return baseMapper.selectOrderDetail(orderId);
    }
    
    @Override
    public boolean payOrder(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null || order.getStatus() != 0) {
            return false;
        }
        
        order.setStatus(1); // 已支付
        order.setPayTime(LocalDateTime.now());
        return this.updateById(order);
    }
    
    @Override
    public List<Map<String, Object>> getMonthlySales() {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.in("status", 1, 2, 3); // 已支付、已发货、已完成
        List<Order> orders = this.list(wrapper);
        
        Map<String, BigDecimal> monthlyMap = orders.stream()
                .collect(Collectors.groupingBy(
                    order -> order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    Collectors.reducing(BigDecimal.ZERO, Order::getTotalAmount, BigDecimal::add)
                ));
        
        return monthlyMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new java.util.HashMap<>();
                    map.put("month", entry.getKey());
                    map.put("turnover", entry.getValue());
                    return map;
                })
                .sorted((a, b) -> a.get("month").toString().compareTo(b.get("month").toString()))
                .collect(Collectors.toList());
    }
}

