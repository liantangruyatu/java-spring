package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户管理控制器（管理员）
 */
@Controller
@RequestMapping("/admin")
public class AdminUserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 检查管理员权限
     */
    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == 1;
    }
    
    /**
     * 用户列表
     */
    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    /**
     * 编辑用户页面
     */
    @GetMapping("/user/edit/{id}")
    public String editUserPage(@PathVariable Long id, HttpSession session, Model model) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin/user-form";
    }
    
    /**
     * 保存用户
     */
    @PostMapping("/user/save")
    public String saveUser(User user, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        if (user.getUserId() == null) {
            userService.register(user);
        } else {
            userService.updateUserInfo(user);
        }
        return "redirect:/admin/users";
    }
    
    /**
     * 删除用户
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!checkAdmin(session)) {
            return "redirect:/login";
        }
        
        userService.removeById(id);
        return "redirect:/admin/users";
    }
}

