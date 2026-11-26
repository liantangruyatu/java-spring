package com.system.mapper;

import com.system.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {
    Product findById(@Param("id") Integer id);
    
    List<Product> findAll();
    
    List<Product> findByMerchantId(@Param("merchantId") Integer merchantId);
    
    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId);
    
    List<Product> findByKeyword(@Param("keyword") String keyword);
    
    int insert(Product product);
    
    int update(Product product);
    
    int deleteById(@Param("id") Integer id);
    
    int updateStock(@Param("id") Integer id, @Param("quantity") Integer quantity);
    
    List<Product> findForSale();
}