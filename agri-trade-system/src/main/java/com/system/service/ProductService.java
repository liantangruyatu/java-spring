package com.system.service;

import com.system.entity.Product;
import java.util.List;

public interface ProductService {
    Product findById(Integer id);
    
    List<Product> findAll();
    
    List<Product> findByMerchantId(Integer merchantId);
    
    List<Product> findByCategoryId(Integer categoryId);
    
    List<Product> findByKeyword(String keyword);
    
    List<Product> findForSale();
    
    Product create(Product product);
    
    Product update(Product product);
    
    boolean deleteById(Integer id);
    
    boolean updateStock(Integer id, Integer quantity);
    
    // 统计销售情况
    List<Product> getSalesStatistics(Integer merchantId);
}