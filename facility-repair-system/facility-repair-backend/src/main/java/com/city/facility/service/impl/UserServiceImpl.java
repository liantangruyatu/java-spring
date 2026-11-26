package com.city.facility.service.impl;

import com.city.facility.dto.UserResponse;
import com.city.facility.entity.User;
import com.city.facility.repository.UserRepository;
import com.city.facility.service.UserService;
import com.city.facility.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User register(String phone, String password, String realName, String userType) {
        // 检查手机号是否已注册
        if (userRepository.existsByPhone(phone)) {
            throw new RuntimeException("手机号已被注册");
        }
        
        // 直接使用明文密码
        User user = User.builder()
                .phone(phone)
                .password(password)
                .realName(realName)
                .userType(User.UserType.valueOf(userType.toUpperCase()))
                .status(User.UserStatus.ACTIVE)
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("用户注册成功: {}", phone);
        return savedUser;
    }
    
    @Override
    public Optional<User> login(String phone, String password) {
        Optional<User> userOpt = userRepository.findByPhone(phone);
        
        if (!userOpt.isPresent()) {
            log.warn("登录失败: 用户不存在, phone={}", phone);
            return Optional.empty();
        }
        
        User user = userOpt.get();
        
        // 直接比较密码（明文）
        if (!password.equals(user.getPassword())) {
            log.warn("登录失败: 密码错误, phone={}", phone);
            return Optional.empty();
        }
        
        // 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            log.warn("登录失败: 用户状态非激活, phone={}", phone);
            return Optional.empty();
        }
        
        log.info("用户登录成功: {}", phone);
        return Optional.of(user);
    }
    
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    
    @Override
    public List<User> getWorkers() {
        return userRepository.findByUserType(User.UserType.WORKER);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User updateUser(Long id, String realName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        user.setRealName(realName);
        return userRepository.save(user);
    }
    
    @Override
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        return UserResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .realName(user.getRealName())
                .userType(user.getUserType().toString())
                .status(user.getStatus().toString())
                .createTime(DateTimeUtil.formatDateTime(user.getCreateTime()))
                .updateTime(DateTimeUtil.formatDateTime(user.getUpdateTime()))
                .build();
    }
    
    /**
     * 加密密码(简单实现，生产环境应使用BCrypt)
     */
    @Override
    public String encryptPassword(String password) {
        // 这里使用简单的实现，实际应该使用 BCryptPasswordEncoder
        return password;  // TODO: 使用真正的密码加密
    }
    
    /**
     * 验证密码
     */
    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        // 简单实现，实际应该使用 BCryptPasswordEncoder.matches()
        return rawPassword.equals(encodedPassword);  // TODO: 使用真正的密码验证
    }
}
