package com.nuc.secondhand.controller;

import com.nuc.secondhand.dto.ApiResponse;
import com.nuc.secondhand.entity.Category;
import com.nuc.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "http://localhost:5173"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ApiResponse.success("分类创建成功", createdCategory);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<Category> getCategoryById(@PathVariable Integer categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ApiResponse.success(category);
        } catch (Exception e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ApiResponse.success(categories);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<Category> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody Category categoryDTO) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
            return ApiResponse.success("分类更新成功", updatedCategory);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable Integer categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ApiResponse.success("分类删除成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
