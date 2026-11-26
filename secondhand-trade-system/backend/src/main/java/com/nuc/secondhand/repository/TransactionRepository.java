package com.nuc.secondhand.repository;

import com.nuc.secondhand.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findByOrderId(Integer orderId, Pageable pageable);
    Page<Transaction> findByBuyerId(Integer buyerId, Pageable pageable);
    Page<Transaction> findBySellerId(Integer sellerId, Pageable pageable);
}
