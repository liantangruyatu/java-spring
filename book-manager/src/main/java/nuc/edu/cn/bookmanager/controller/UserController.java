package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 个人信息页面
     */
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userService.getById(user.getUserId()));
        return "user/profile";
    }
    
    /**
     * 更新个人信息
     */
    @PostMapping("/profile")
    public String updateProfile(User user, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        user.setUserId(sessionUser.getUserId());
        user.setRole(sessionUser.getRole()); // 不允许修改角色
        if (userService.updateUserInfo(user)) {
            session.setAttribute("user", userService.getById(user.getUserId()));
            model.addAttribute("message", "更新成功");
        } else {
            model.addAttribute("error", "更新失败");
        }
        return "user/profile";
    }
}

