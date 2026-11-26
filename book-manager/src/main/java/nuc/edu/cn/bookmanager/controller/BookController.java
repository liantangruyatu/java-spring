package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.dto.BookDTO;
import nuc.edu.cn.bookmanager.entity.Book;
import nuc.edu.cn.bookmanager.entity.Category;
import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.BookService;
import nuc.edu.cn.bookmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * 图书控制器（用户端）
 */
@Controller
@RequestMapping("/user")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 图书列表页面
     */
    @GetMapping("/books")
    public String books(@RequestParam(required = false) String bookName,
                       @RequestParam(required = false) Integer categoryId,
                       @RequestParam(required = false) BigDecimal minPrice,
                       @RequestParam(required = false) BigDecimal maxPrice,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       HttpSession session,
                       Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        int pageSize = 12;
        List<BookDTO> books = bookService.searchBooks(bookName, categoryId, minPrice, maxPrice, pageNum, pageSize);
        // 只加载有图书的类别
        List<Category> categories = categoryService.getCategoriesWithBooks();
        
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("bookName", bookName);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("user", user);
        
        return "user/books";
    }
    
    /**
     * 图书详情页面
     */
    @GetMapping("/book/{id}")
    public String bookDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Book book = bookService.getById(id);
        if (book == null) {
            return "redirect:/user/books";
        }
        
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        return "user/book-detail";
    }
}

