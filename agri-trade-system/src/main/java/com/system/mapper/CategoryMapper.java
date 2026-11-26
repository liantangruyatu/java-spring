package com.system.mapper;

import com.system.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CategoryMapper {
    Category findById(@Param("id") Integer id);
    
    List<Category> findAll();
    
    List<Category> findByParentId(@Param("parentId") Integer parentId);
    
    int insert(Category category);
    
    int update(Category category);
    
    int deleteById(@Param("id") Integer id);
    
    List<Category> findAllWithChildren();
}