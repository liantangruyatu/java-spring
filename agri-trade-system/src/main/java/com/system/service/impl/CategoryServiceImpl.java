package com.system.service.impl;

import com.system.entity.Category;
import com.system.mapper.CategoryMapper;
import com.system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }
    
    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
    
    @Override
    public List<Category> findByParentId(Integer parentId) {
        return categoryMapper.findByParentId(parentId);
    }
    
    @Override
    public List<Category> findAllWithChildren() {
        return categoryMapper.findAllWithChildren();
    }
    
    @Override
    public Category create(Category category) {
        categoryMapper.insert(category);
        return category;
    }
    
    @Override
    public Category update(Category category) {
        categoryMapper.update(category);
        return category;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return categoryMapper.deleteById(id) > 0;
    }
}