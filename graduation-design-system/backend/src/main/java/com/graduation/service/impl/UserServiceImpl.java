package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.User;
import com.graduation.mapper.UserMapper;
import com.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User findByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).one();
    }
    
    @Override
    public User login(String username, String password) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password)
                .eq(User::getStatus, 1) // 只允许启用状态的用户登录
                .one();
    }
}
