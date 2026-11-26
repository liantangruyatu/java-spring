package com.nuc.secondhand.repository;

import com.nuc.secondhand.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findByBuyerId(Integer buyerId, Pageable pageable);
    Page<Order> findBySellerId(Integer sellerId, Pageable pageable);
    Page<Order> findByOrderStatus(String orderStatus, Pageable pageable);
}
