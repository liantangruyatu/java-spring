package nuc.edu.cn.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nuc.edu.cn.bookmanager.entity.Book;
import nuc.edu.cn.bookmanager.entity.Category;
import nuc.edu.cn.bookmanager.dto.BookDTO;
import nuc.edu.cn.bookmanager.mapper.BookMapper;
import nuc.edu.cn.bookmanager.service.BookService;
import nuc.edu.cn.bookmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图书服务实现类
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    
    @Autowired
    private CategoryService categoryService;
    
    @Override
    public List<BookDTO> searchBooks(String bookName, Integer categoryId, 
                                     BigDecimal minPrice, BigDecimal maxPrice,
                                     Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return baseMapper.searchBooks(bookName, categoryId, minPrice, maxPrice, offset, pageSize);
    }
    
    @Override
    public BookDTO getBookDetail(Long bookId) {
        Book book = this.getById(bookId);
        if (book == null) {
            return null;
        }
        
        BookDTO dto = new BookDTO();
        BeanUtils.copyProperties(book, dto);
        
        // 查询类别名称
        if (book.getCategoryId() != null) {
            Category category = categoryService.getById(book.getCategoryId());
            if (category != null) {
                dto.setCategoryName(category.getCategoryName());
            }
        }
        
        return dto;
    }
    
    @Override
    public List<Book> getTopSalesBooks() {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sales_volume");
        wrapper.last("LIMIT 10");
        return this.list(wrapper);
    }
}

