package nuc.edu.cn.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nuc.edu.cn.bookmanager.entity.Book;
import nuc.edu.cn.bookmanager.dto.BookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图书Mapper接口
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    
    /**
     * 多条件查询图书
     */
    List<BookDTO> searchBooks(@Param("bookName") String bookName,
                              @Param("categoryId") Integer categoryId,
                              @Param("minPrice") BigDecimal minPrice,
                              @Param("maxPrice") BigDecimal maxPrice,
                              @Param("offset") Integer offset,
                              @Param("limit") Integer limit);
    
    /**
     * 统计查询结果数量
     */
    Long countBooks(@Param("bookName") String bookName,
                   @Param("categoryId") Integer categoryId,
                   @Param("minPrice") BigDecimal minPrice,
                   @Param("maxPrice") BigDecimal maxPrice);
}

