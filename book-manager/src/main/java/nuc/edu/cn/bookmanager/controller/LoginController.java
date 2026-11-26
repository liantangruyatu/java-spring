package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 */
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String userName, 
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        User user = userService.login(userName, password);
        if (user != null) {
            session.setAttribute("user", user);
            if (user.getRole() == 1) {
                return "redirect:/admin/books";
            } else {
                return "redirect:/user/books";
            }
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(User user, Model model) {
        if (userService.register(user)) {
            model.addAttribute("message", "注册成功，请登录");
            return "login";
        } else {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
    }
}

