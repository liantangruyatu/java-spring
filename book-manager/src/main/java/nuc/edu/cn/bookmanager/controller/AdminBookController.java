package nuc.edu.cn.bookmanager.controller;

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
import java.util.List;

/**
 * 图书管理控制器（管理员）
 */
@Controller
@RequestMapping("/admin")
public class AdminBookController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 检查管理员权限
     */
    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == 1;
    }
    
    /**
     * 图书列表
     */
    @GetMapping("/books")
    public String books(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        List<Book> books = bookService.list();
        List<Category> categories = categoryService.list();
        
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        return "admin/books";
    }
    
    /**
     * 添加图书页面
     */
    @GetMapping("/book/add")
    public String addBookPage(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        // 创建空的Book对象，用于表单绑定
        Book book = new Book();
        List<Category> categories = categoryService.list();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "admin/book-form";
    }
    
    /**
     * 编辑图书页面
     */
    @GetMapping("/book/edit/{id}")
    public String editBookPage(@PathVariable Long id, HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        Book book = bookService.getById(id);
        List<Category> categories = categoryService.list();
        
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "admin/book-form";
    }
    
    /**
     * 保存图书
     */
    @PostMapping("/book/save")
    public String saveBook(Book book, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        if (book.getBookId() == null) {
            bookService.save(book);
        } else {
            bookService.updateById(book);
        }
        return "redirect:/admin/books";
    }
    
    /**
     * 删除图书
     */
    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        bookService.removeById(id);
        return "redirect:/admin/books";
    }
    
    /**
     * 销量统计
     */
    @GetMapping("/statistics")
    public String statistics(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        List<Book> topBooks = bookService.getTopSalesBooks();
        model.addAttribute("topBooks", topBooks);
        return "admin/statistics";
    }
}

