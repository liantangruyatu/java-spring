package com.nuc.secondhand.service;

import com.nuc.secondhand.dto.ProductDTO;
import com.nuc.secondhand.entity.Category;
import com.nuc.secondhand.entity.Product;
import com.nuc.secondhand.entity.User;
import com.nuc.secondhand.repository.CategoryRepository;
import com.nuc.secondhand.repository.ProductRepository;
import com.nuc.secondhand.repository.UserRepository;
import com.nuc.secondhand.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Optional<User> sellerOptional = userRepository.findById(productDTO.getSellerId());
        if (!sellerOptional.isPresent()) {
            throw new RuntimeException("卖家不存在");
        }

        Optional<Category> categoryOptional = categoryRepository.findById(productDTO.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("分类不存在");
        }

        Product product = Product.builder()
                .sellerId(productDTO.getSellerId())
                .categoryId(productDTO.getCategoryId())
                .productName(productDTO.getProductName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .condition(productDTO.getCondition())
                .imageUrl(productDTO.getImageUrl())
                .publishTime(LocalDateTime.now())
                .saleStartTime(productDTO.getSaleStartTime() != null ? DateUtil.parseDateTime(productDTO.getSaleStartTime()) : LocalDateTime.now())
                .saleEndTime(productDTO.getSaleEndTime() != null ? DateUtil.parseDateTime(productDTO.getSaleEndTime()) : null)
                .productStatus("在售")
                .viewCount(0)
                .location(productDTO.getLocation())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO getProductById(Integer productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("物品不存在");
        }

        Product product = productOptional.get();
        // 增加浏览次数
        product.setViewCount(product.getViewCount() + 1);
        productRepository.save(product);

        return convertToDTO(product);
    }

    public Page<ProductDTO> searchProducts(String productName, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameContaining(productName, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsByCategory(Integer categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsBySeller(Integer sellerId, Pageable pageable) {
        Page<Product> products = productRepository.findBySellerId(sellerId, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getOnSaleProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByProductStatus("在售", pageable);
        return products.map(this::convertToDTO);
    }

    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("物品不存在");
        }

        Product product = productOptional.get();
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCondition(productDTO.getCondition());
        product.setImageUrl(productDTO.getImageUrl());
        product.setLocation(productDTO.getLocation());

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    public void changeProductStatus(Integer productId, String status) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("物品不存在");
        }

        Product product = productOptional.get();
        product.setProductStatus(status);
        productRepository.save(product);
    }

    private ProductDTO convertToDTO(Product product) {
        String sellerName = "";
        String categoryName = "";
        
        // 获取卖家信息
        if (product.getSellerId() != null) {
            try {
                User seller = userRepository.findById(product.getSellerId()).orElse(null);
                if (seller != null) {
                    sellerName = seller.getRealName() != null ? seller.getRealName() : "";
                }
            } catch (Exception e) {
                System.err.println("获取卖家失败: " + e.getMessage());
            }
        }
        
        // 获取分类信息
        if (product.getCategoryId() != null) {
            try {
                Category category = categoryRepository.findById(product.getCategoryId()).orElse(null);
                if (category != null) {
                    categoryName = category.getCategoryName() != null ? category.getCategoryName() : "";
                }
            } catch (Exception e) {
                System.err.println("获取分类失败: " + e.getMessage());
            }
        }

        return ProductDTO.builder()
                .productId(product.getProductId())
                .sellerId(product.getSellerId())
                .sellerName(sellerName)
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .condition(product.getCondition())
                .imageUrl(product.getImageUrl())
                .publishTime(DateUtil.formatDateTime(product.getPublishTime()))
                .saleStartTime(DateUtil.formatDateTime(product.getSaleStartTime()))
                .saleEndTime(DateUtil.formatDateTime(product.getSaleEndTime()))
                .productStatus(product.getProductStatus())
                .viewCount(product.getViewCount())
                .location(product.getLocation())
                .build();
    }
}
