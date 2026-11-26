package com.nuc.secondhand.service;

import com.nuc.secondhand.entity.Category;
import com.nuc.secondhand.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("分类不存在");
        }
        return categoryOptional.get();
    }

    public Category getCategoryByName(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("分类不存在");
        }
        return categoryOptional.get();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Integer categoryId, Category categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("分类不存在");
        }

        Category category = categoryOptional.get();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category.setIconUrl(categoryDTO.getIconUrl());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
