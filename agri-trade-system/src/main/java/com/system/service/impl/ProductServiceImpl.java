package com.system.service.impl;

import com.system.entity.Product;
import com.system.mapper.ProductMapper;
import com.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public Product findById(Integer id) {
        return productMapper.findById(id);
    }
    
    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
    
    @Override
    public List<Product> findByMerchantId(Integer merchantId) {
        return productMapper.findByMerchantId(merchantId);
    }
    
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productMapper.findByCategoryId(categoryId);
    }
    
    @Override
    public List<Product> findByKeyword(String keyword) {
        return productMapper.findByKeyword(keyword);
    }
    
    @Override
    public List<Product> findForSale() {
        return productMapper.findForSale();
    }
    
    @Override
    public Product create(Product product) {
        productMapper.insert(product);
        return product;
    }
    
    @Override
    public Product update(Product product) {
        productMapper.update(product);
        return product;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return productMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean updateStock(Integer id, Integer quantity) {
        return productMapper.updateStock(id, quantity) > 0;
    }
    
    @Override
    public List<Product> getSalesStatistics(Integer merchantId) {
        // 这里需要根据订单数据来统计销售情况，实际实现可能需要关联订单表
        // 为了简化，我们返回商家的所有产品
        return productMapper.findByMerchantId(merchantId);
    }
}