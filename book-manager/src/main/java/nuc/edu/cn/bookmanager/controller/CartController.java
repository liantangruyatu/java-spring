package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.entity.Cart;
import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.BookService;
import nuc.edu.cn.bookmanager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车控制器
 */
@Controller
@RequestMapping("/user")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private BookService bookService;
    
    /**
     * 购物车页面
     */
    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Cart> carts = cartService.getUserCart(user.getUserId());
        
        model.addAttribute("carts", carts);
        model.addAttribute("books", carts.stream()
                .collect(Collectors.toMap(Cart::getBookId, 
                    cart -> bookService.getById(cart.getBookId()))));
        return "user/cart";
    }
    
    /**
     * 添加到购物车
     */
    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(@RequestParam Long bookId, 
                           @RequestParam Integer quantity,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        
        if (cartService.addToCart(user.getUserId(), bookId, quantity)) {
            return "success";
        } else {
            return "库存不足";
        }
    }
    
    /**
     * 更新购物车数量
     */
    @PostMapping("/cart/update")
    @ResponseBody
    public String updateCart(@RequestParam Long cartId,
                            @RequestParam Integer quantity,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        
        if (cartService.updateCartQuantity(cartId, quantity)) {
            return "success";
        } else {
            return "更新失败";
        }
    }
    
    /**
     * 删除购物车项
     */
    @GetMapping("/cart/delete/{id}")
    public String deleteCart(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        cartService.removeById(id);
        return "redirect:/user/cart";
    }
}

