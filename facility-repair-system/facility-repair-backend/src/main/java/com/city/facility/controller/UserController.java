package com.city.facility.controller;

import com.city.facility.dto.ApiResponse;
import com.city.facility.dto.UserLoginRequest;
import com.city.facility.dto.UserRegisterRequest;
import com.city.facility.dto.UserResponse;
import com.city.facility.entity.User;
import com.city.facility.service.UserService;
import com.city.facility.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.city.facility.repository.UserRepository;
import java.util.List;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkerService workerService;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        try {
            User user = userService.register(request.getPhone(), request.getPassword(), 
                    request.getRealName(), request.getUserType());
            
            // 如果是维修工，创建维修工记录
            if ("worker".equals(request.getUserType())) {
                workerService.createWorker(user.getId(), null, request.getPhone());
            }
            
            return ApiResponse.success("注册成功", userService.toResponse(user));
        } catch (RuntimeException e) {
            log.error("注册失败: {}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        }
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody UserLoginRequest request) {
        try {
            Optional<User> userOpt = userService.login(request.getPhone(), request.getPassword());
            
            if (!userOpt.isPresent()) {
                return ApiResponse.error(401, "手机号或密码错误");
            }
            
            return ApiResponse.success("登录成功", userService.toResponse(userOpt.get()));
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        try {
            Optional<User> userOpt = userService.getUserById(id);
            
            if (!userOpt.isPresent()) {
                return ApiResponse.notFound("用户不存在");
            }
            
            return ApiResponse.success(userService.toResponse(userOpt.get()));
        } catch (Exception e) {
            log.error("获取用户信息失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserResponse request) {
        try {
            User user = userService.updateUser(id, request.getRealName());
            return ApiResponse.success("更新成功", userService.toResponse(user));
        } catch (Exception e) {
            log.error("更新用户信息失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 测试接口
     */
    @GetMapping("/test")
    public ApiResponse<String> test() {
        return ApiResponse.success("后端服务正常运行");
    }
    
    /**
     * 查看所有用户数据（测试用）
     */
    @GetMapping("/all")
    public ApiResponse<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ApiResponse.success("查询成功", users);
        } catch (Exception e) {
            log.error("查询用户数据失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 批量更新所有用户密码
     */
    @PostMapping("/update-all-passwords")
    public ApiResponse<String> updateAllPasswords() {
        try {
            // 直接使用SQL更新所有用户密码，避免枚举转换问题
            String plainPassword = "administer";
            int count = userRepository.updateAllPasswords(plainPassword);
            
            return ApiResponse.success("所有用户密码已更新为administer", "共更新了" + count + "个用户");
        } catch (Exception e) {
            log.error("批量更新密码失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
}
