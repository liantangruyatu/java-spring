package com.system.service;

import com.system.entity.Category;
import java.util.List;

public interface CategoryService {
    Category findById(Integer id);
    
    List<Category> findAll();
    
    List<Category> findByParentId(Integer parentId);
    
    List<Category> findAllWithChildren();
    
    Category create(Category category);
    
    Category update(Category category);
    
    boolean deleteById(Integer id);
}