package com.system.service.impl;

import com.system.entity.User;
import com.system.mapper.UserMapper;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return user;
        }
        return null;
    }
    
    @Override
    public User register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        existingUser = userMapper.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("邮箱已存在");
        }
        
        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
    
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
    
    @Override
    public List<User> findByRole(String role) {
        return userMapper.findByRole(role);
    }
    
    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            return false;
        }
        
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        return userMapper.update(user) > 0;
    }
}