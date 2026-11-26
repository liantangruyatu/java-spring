package com.system.controller;

import com.system.entity.Category;
import com.system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    // 获取分类详情
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Category category = categoryService.findById(id);
        if (category != null) {
            result.put("success", true);
            result.put("data", category);
        } else {
            result.put("success", false);
            result.put("message", "分类不存在");
        }
        return ResponseEntity.ok(result);
    }
    
    // 获取所有分类
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> result = new HashMap<>();
        List<Category> categories = categoryService.findAll();
        result.put("success", true);
        result.put("data", categories);
        return ResponseEntity.ok(result);
    }
    
    // 获取子分类
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<Map<String, Object>> getCategoriesByParentId(@PathVariable Integer parentId) {
        Map<String, Object> result = new HashMap<>();
        List<Category> categories = categoryService.findByParentId(parentId);
        result.put("success", true);
        result.put("data", categories);
        return ResponseEntity.ok(result);
    }
    
    // 获取所有分类（包含子分类）
    @GetMapping("/tree")
    public ResponseEntity<Map<String, Object>> getAllCategoriesWithChildren() {
        Map<String, Object> result = new HashMap<>();
        List<Category> categories = categoryService.findAllWithChildren();
        result.put("success", true);
        result.put("data", categories);
        return ResponseEntity.ok(result);
    }
    
    // 创建分类
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Category category) {
        Map<String, Object> result = new HashMap<>();
        Category newCategory = categoryService.create(category);
        result.put("success", true);
        result.put("message", "分类创建成功");
        result.put("data", newCategory);
        return ResponseEntity.ok(result);
    }
    
    // 更新分类
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCategory(@RequestBody Category category) {
        Map<String, Object> result = new HashMap<>();
        Category updatedCategory = categoryService.update(category);
        if (updatedCategory != null) {
            result.put("success", true);
            result.put("message", "分类更新成功");
            result.put("data", updatedCategory);
        } else {
            result.put("success", false);
            result.put("message", "分类更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 删除分类
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = categoryService.deleteById(id);
        if (success) {
            result.put("success", true);
            result.put("message", "分类删除成功");
        } else {
            result.put("success", false);
            result.put("message", "分类删除失败");
        }
        return ResponseEntity.ok(result);
    }
}