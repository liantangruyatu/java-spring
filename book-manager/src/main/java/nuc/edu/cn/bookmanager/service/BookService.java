package nuc.edu.cn.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nuc.edu.cn.bookmanager.entity.Book;
import nuc.edu.cn.bookmanager.dto.BookDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图书服务接口
 */
public interface BookService extends IService<Book> {
    
    /**
     * 多条件查询图书
     */
    List<BookDTO> searchBooks(String bookName, Integer categoryId, 
                             BigDecimal minPrice, BigDecimal maxPrice,
                             Integer pageNum, Integer pageSize);
    
    /**
     * 获取图书详情（包含类别名称）
     */
    BookDTO getBookDetail(Long bookId);
    
    /**
     * 获取销量TOP10
     */
    List<Book> getTopSalesBooks();
}

