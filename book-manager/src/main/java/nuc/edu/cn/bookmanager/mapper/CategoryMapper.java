package nuc.edu.cn.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nuc.edu.cn.bookmanager.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 类别Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    
    /**
     * 查询有图书的类别
     */
    @Select("SELECT DISTINCT c.* FROM t_category c " +
            "INNER JOIN t_book b ON c.category_id = b.category_id " +
            "ORDER BY c.category_id")
    List<Category> selectCategoriesWithBooks();
}

