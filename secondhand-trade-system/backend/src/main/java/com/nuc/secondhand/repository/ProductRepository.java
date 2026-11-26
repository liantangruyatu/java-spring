package com.nuc.secondhand.repository;

import com.nuc.secondhand.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByProductStatus(String status, Pageable pageable);
    Page<Product> findBySellerId(Integer sellerId, Pageable pageable);
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
    List<Product> findBySellerIdAndProductStatus(Integer sellerId, String status);
}
