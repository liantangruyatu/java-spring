package com.nuc.secondhand.controller;

import com.nuc.secondhand.dto.ApiResponse;
import com.nuc.secondhand.dto.ProductDTO;
import com.nuc.secondhand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "http://localhost:5173"})
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ApiResponse<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.createProduct(productDTO);
            return ApiResponse.success("物品发布成功", createdProduct);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductDTO> getProductById(@PathVariable Integer productId) {
        try {
            ProductDTO productDTO = productService.getProductById(productId);
            return ApiResponse.success(productDTO);
        } catch (Exception e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductDTO>> searchProducts(
            @RequestParam String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> products = productService.searchProducts(productName, pageable);
            return ApiResponse.success(products);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<ProductDTO>> getProductsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> products = productService.getProductsByCategory(categoryId, pageable);
            return ApiResponse.success(products);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ApiResponse<Page<ProductDTO>> getProductsBySeller(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> products = productService.getProductsBySeller(sellerId, pageable);
            return ApiResponse.success(products);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/on-sale")
    public ApiResponse<Page<ProductDTO>> getOnSaleProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductDTO> products = productService.getOnSaleProducts(pageable);
            return ApiResponse.success(products);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductDTO> updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
            return ApiResponse.success("物品更新成功", updatedProduct);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Integer productId) {
        try {
            productService.deleteProduct(productId);
            return ApiResponse.success("物品删除成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @PutMapping("/{productId}/status")
    public ApiResponse<String> changeProductStatus(
            @PathVariable Integer productId,
            @RequestParam String status) {
        try {
            productService.changeProductStatus(productId, status);
            return ApiResponse.success("物品状态更新成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
