package com.example.volunteer.controller;

import com.example.volunteer.model.User;
import com.example.volunteer.service.UserService;
import com.example.volunteer.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;
    private final VolunteerService volunteerService;

    public AuthController(UserService userService, VolunteerService volunteerService) {
        this.userService = userService;
        this.volunteerService = volunteerService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
        session.setAttribute("user", userOpt.get());
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String fullName,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false) String email,
                             Model model) {
        if (userService.findByUsername(username).isPresent()) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        User user = userService.registerVolunteerUser(username, password, fullName, phone, email);
        volunteerService.createVolunteerForUser(user);
        model.addAttribute("msg", "注册成功，请登录");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}



