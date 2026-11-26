package nuc.edu.cn.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nuc.edu.cn.bookmanager.entity.Category;
import nuc.edu.cn.bookmanager.mapper.CategoryMapper;
import nuc.edu.cn.bookmanager.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类别服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    
    @Override
    public List<Category> getRootCategories() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        return this.list(wrapper);
    }
    
    @Override
    public List<Category> getCategoriesByParentId(Integer parentId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        return this.list(wrapper);
    }
    
    @Override
    public List<Category> getAllCategoriesOrdered() {
        // 先获取所有根类别，然后获取子类别，按层级排序
        List<Category> allCategories = this.list();
        // 按parent_id和category_id排序，根类别在前，子类别在后
        allCategories.sort((a, b) -> {
            if (a.getParentId() == 0 && b.getParentId() != 0) {
                return -1;
            }
            if (a.getParentId() != 0 && b.getParentId() == 0) {
                return 1;
            }
            if (a.getParentId().equals(b.getParentId())) {
                return a.getCategoryId().compareTo(b.getCategoryId());
            }
            return a.getParentId().compareTo(b.getParentId());
        });
        return allCategories;
    }
    
    @Override
    public List<Category> getCategoriesWithBooks() {
        return baseMapper.selectCategoriesWithBooks();
    }
}

