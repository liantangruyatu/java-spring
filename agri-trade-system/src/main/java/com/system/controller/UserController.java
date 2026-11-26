package com.system.controller;

import com.system.entity.User;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("data", loginUser);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return ResponseEntity.ok(result);
    }
    
    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            User registeredUser = userService.register(user);
            result.put("success", true);
            result.put("message", "注册成功");
            result.put("data", registeredUser);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
    
    // 获取用户信息
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.findById(id);
        if (user != null) {
            result.put("success", true);
            result.put("data", user);
        } else {
            result.put("success", false);
            result.put("message", "用户不存在");
        }
        return ResponseEntity.ok(result);
    }
    
    // 更新用户信息
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        User updatedUser = userService.update(user);
        if (updatedUser != null) {
            result.put("success", true);
            result.put("message", "更新成功");
            result.put("data", updatedUser);
        } else {
            result.put("success", false);
            result.put("message", "更新失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 修改密码
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestParam Integer userId, 
                                                              @RequestParam String oldPassword, 
                                                              @RequestParam String newPassword) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.changePassword(userId, oldPassword, newPassword);
        if (success) {
            result.put("success", true);
            result.put("message", "密码修改成功");
        } else {
            result.put("success", false);
            result.put("message", "原密码错误或用户不存在");
        }
        return ResponseEntity.ok(result);
    }
    
    // 管理员获取所有用户
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userService.findAll();
        result.put("success", true);
        result.put("data", users);
        return ResponseEntity.ok(result);
    }
    
    // 管理员根据角色获取用户
    @GetMapping("/role/{role}")
    public ResponseEntity<Map<String, Object>> getUsersByRole(@PathVariable String role) {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userService.findByRole(role);
        result.put("success", true);
        result.put("data", users);
        return ResponseEntity.ok(result);
    }
    
    // 管理员删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.deleteById(id);
        if (success) {
            result.put("success", true);
            result.put("message", "删除成功");
        } else {
            result.put("success", false);
            result.put("message", "删除失败");
        }
        return ResponseEntity.ok(result);
    }
}