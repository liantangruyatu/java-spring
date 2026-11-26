package com.city.facility.service;

import com.city.facility.dto.UserResponse;
import com.city.facility.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 用户注册
     */
    User register(String phone, String password, String realName, String userType);
    
    /**
     * 用户登录验证
     */
    Optional<User> login(String phone, String password);
    
    /**
     * 根据ID查询用户
     */
    Optional<User> getUserById(Long id);
    
    /**
     * 根据手机号查询用户
     */
    Optional<User> getUserByPhone(String phone);
    
    /**
     * 查询所有维修工
     */
    List<User> getWorkers();
    
    /**
     * 查询所有用户
     */
    List<User> getAllUsers();
    
    /**
     * 更新用户信息
     */
    User updateUser(Long id, String realName);
    
    /**
     * 转换为响应对象
     */
    UserResponse toResponse(User user);
    
    /**
     * 加密密码
     */
    String encryptPassword(String password);
}
