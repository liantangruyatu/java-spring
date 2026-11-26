package com.system.controller;

import com.system.entity.Product;
import com.system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // 获取商品详情
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Product product = productService.findById(id);
        if (product != null) {
            result.put("success", true);
            result.put("data", product);
        } else {
            result.put("success", false);
            result.put("message", "商品不存在");
        }
        return ResponseEntity.ok(result);
    }
    
    // 获取所有商品
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.findAll();
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
    
    // 获取商家商品
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<Map<String, Object>> getProductsByMerchantId(@PathVariable Integer merchantId) {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.findByMerchantId(merchantId);
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
    
    // 获取分类商品
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Map<String, Object>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.findByCategoryId(categoryId);
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
    
    // 搜索商品
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProducts(@RequestParam String keyword) {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.findByKeyword(keyword);
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
    
    // 获取在售商品
    @GetMapping("/onsale")
    public ResponseEntity<Map<String, Object>> getProductsForSale() {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.findForSale();
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
    
    // 创建商品（商家）
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        Product newProduct = productService.create(product);
        result.put("success", true);
        result.put("message", "商品创建成功");
        result.put("data", newProduct);
        return ResponseEntity.ok(result);
    }
    
    // 更新商品（商家）
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody Product product) {
        Map<String, Object> result = new HashMap<>();
        Product updatedProduct = productService.update(product);
        if (updatedProduct != null) {
            result.put("success", true);
            result.put("message", "商品更新成功");
            result.put("data", updatedProduct);
        } else {
            result.put("success", false);
            result.put("message", "商品更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 删除商品（商家）
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = productService.deleteById(id);
        if (success) {
            result.put("success", true);
            result.put("message", "商品删除成功");
        } else {
            result.put("success", false);
            result.put("message", "商品删除失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 更新库存（商家）
    @PutMapping("/stock/{id}")
    public ResponseEntity<Map<String, Object>> updateStock(@PathVariable Integer id, @RequestParam Integer quantity) {
        Map<String, Object> result = new HashMap<>();
        boolean success = productService.updateStock(id, quantity);
        if (success) {
            result.put("success", true);
            result.put("message", "库存更新成功");
        } else {
            result.put("success", false);
            result.put("message", "库存更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 获取销售统计（商家）
    @GetMapping("/sales/{merchantId}")
    public ResponseEntity<Map<String, Object>> getSalesStatistics(@PathVariable Integer merchantId) {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.getSalesStatistics(merchantId);
        result.put("success", true);
        result.put("data", products);
        return ResponseEntity.ok(result);
    }
}