package nuc.edu.cn.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nuc.edu.cn.bookmanager.entity.Category;

import java.util.List;

/**
 * 类别服务接口
 */
public interface CategoryService extends IService<Category> {
    
    /**
     * 获取所有一级类别
     */
    List<Category> getRootCategories();
    
    /**
     * 根据父类别ID获取子类别
     */
    List<Category> getCategoriesByParentId(Integer parentId);
    
    /**
     * 获取所有类别（按层级排序）
     */
    List<Category> getAllCategoriesOrdered();
    
    /**
     * 获取有图书的类别（只返回实际有图书的类别）
     */
    List<Category> getCategoriesWithBooks();
}

