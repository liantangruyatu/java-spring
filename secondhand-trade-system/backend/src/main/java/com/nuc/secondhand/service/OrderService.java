package com.nuc.secondhand.service;

import com.nuc.secondhand.dto.OrderDTO;
import com.nuc.secondhand.entity.Order;
import com.nuc.secondhand.entity.Product;
import com.nuc.secondhand.entity.User;
import com.nuc.secondhand.repository.OrderRepository;
import com.nuc.secondhand.repository.ProductRepository;
import com.nuc.secondhand.repository.UserRepository;
import com.nuc.secondhand.util.DateUtil;
import com.nuc.secondhand.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Optional<Product> productOptional = productRepository.findById(orderDTO.getProductId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("物品不存在");
        }

        Product product = productOptional.get();
        if (!"在售".equals(product.getProductStatus())) {
            throw new RuntimeException("物品已不在售");
        }

        Optional<User> buyerOptional = userRepository.findById(orderDTO.getBuyerId());
        Optional<User> sellerOptional = userRepository.findById(orderDTO.getSellerId());

        if (!buyerOptional.isPresent() || !sellerOptional.isPresent()) {
            throw new RuntimeException("买家或卖家不存在");
        }

        Order order = Order.builder()
                .orderNumber(OrderUtil.generateOrderNumber())
                .buyerId(orderDTO.getBuyerId())
                .sellerId(orderDTO.getSellerId())
                .productId(orderDTO.getProductId())
                .orderAmount(product.getPrice())
                .orderStatus("待支付")
                .quantity(orderDTO.getQuantity() != null ? orderDTO.getQuantity() : 1)
                .createTime(LocalDateTime.now())
                .transactionAddress(orderDTO.getTransactionAddress())
                .remark(orderDTO.getRemark())
                .build();

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderById(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("订单不存在");
        }
        return convertToDTO(orderOptional.get());
    }

    public Page<OrderDTO> getOrdersByBuyer(Integer buyerId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByBuyerId(buyerId, pageable);
        return orders.map(this::convertToDTO);
    }

    public Page<OrderDTO> getOrdersBySeller(Integer sellerId, Pageable pageable) {
        Page<Order> orders = orderRepository.findBySellerId(sellerId, pageable);
        return orders.map(this::convertToDTO);
    }

    public Page<OrderDTO> getOrdersByStatus(String status, Pageable pageable) {
        Page<Order> orders = orderRepository.findByOrderStatus(status, pageable);
        return orders.map(this::convertToDTO);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Integer orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("订单不存在");
        }

        Order order = orderOptional.get();
        order.setOrderStatus(status);

        if ("已支付".equals(status)) {
            order.setPaymentTime(LocalDateTime.now());
        } else if ("已发货".equals(status)) {
            order.setDeliveryTime(LocalDateTime.now());
        } else if ("已收货".equals(status)) {
            order.setReceiptTime(LocalDateTime.now());
        } else if ("交易完成".equals(status)) {
            order.setCompletionTime(LocalDateTime.now());
            // 标记物品为已售
            Optional<Product> productOptional = productRepository.findById(order.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setProductStatus("已售");
                productRepository.save(product);
            }
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO rateOrder(Integer orderId, Integer score, String comment, String rateType) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("订单不存在");
        }

        Order order = orderOptional.get();
        if ("buyer".equals(rateType)) {
            order.setBuyerRateScore(score);
            order.setBuyerRateComment(comment);
        } else {
            order.setSellerRateScore(score);
            order.setSellerRateComment(comment);
        }

        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDTO convertToDTO(Order order) {
        User buyer = userRepository.findById(order.getBuyerId()).orElse(null);
        User seller = userRepository.findById(order.getSellerId()).orElse(null);
        Product product = productRepository.findById(order.getProductId()).orElse(null);

        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .buyerId(order.getBuyerId())
                .buyerName(buyer != null ? buyer.getRealName() : "")
                .sellerId(order.getSellerId())
                .sellerName(seller != null ? seller.getRealName() : "")
                .productId(order.getProductId())
                .productName(product != null ? product.getProductName() : "")
                .orderAmount(order.getOrderAmount())
                .orderStatus(order.getOrderStatus())
                .quantity(order.getQuantity())
                .createTime(DateUtil.formatDateTime(order.getCreateTime()))
                .paymentTime(DateUtil.formatDateTime(order.getPaymentTime()))
                .deliveryTime(DateUtil.formatDateTime(order.getDeliveryTime()))
                .receiptTime(DateUtil.formatDateTime(order.getReceiptTime()))
                .completionTime(DateUtil.formatDateTime(order.getCompletionTime()))
                .buyerRateScore(order.getBuyerRateScore())
                .buyerRateComment(order.getBuyerRateComment())
                .sellerRateScore(order.getSellerRateScore())
                .sellerRateComment(order.getSellerRateComment())
                .transactionAddress(order.getTransactionAddress())
                .remark(order.getRemark())
                .build();
    }
}
