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
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    // 管理员获取所有用户
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userService.findAll();
        result.put("success", true);
        result.put("data", users);
        return ResponseEntity.ok(result);
    }
    
    // 管理员根据角色获取用户
    @GetMapping("/users/role/{role}")
    public ResponseEntity<Map<String, Object>> getUsersByRole(@PathVariable String role) {
        Map<String, Object> result = new HashMap<>();
        List<User> users = userService.findByRole(role);
        result.put("success", true);
        result.put("data", users);
        return ResponseEntity.ok(result);
    }
    
    // 管理员删除用户
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.deleteById(id);
        if (success) {
            result.put("success", true);
            result.put("message", "用户删除成功");
        } else {
            result.put("success", false);
            result.put("message", "用户删除失败");
        }
        return ResponseEntity.ok(result);
    }
    
    // 管理员更新用户状态
    @PutMapping("/user/status")
    public ResponseEntity<Map<String, Object>> updateUserStatus(@RequestParam Integer userId, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.findById(userId);
        if (user != null) {
            user.setStatus(status);
            userService.update(user);
            result.put("success", true);
            result.put("message", "用户状态更新成功");
        } else {
            result.put("success", false);
            result.put("message", "用户不存在");
        }
        return ResponseEntity.ok(result);
    }
}