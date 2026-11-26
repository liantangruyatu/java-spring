package nuc.edu.cn.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.mapper.UserMapper;
import nuc.edu.cn.bookmanager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public User login(String userName, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        wrapper.eq("status", 1);
        User user = this.getOne(wrapper);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    @Override
    public boolean register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        if (this.count(wrapper) > 0) {
            return false; // 用户名已存在
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(0); // 默认普通用户
        user.setStatus(1); // 默认正常
        return this.save(user);
    }
    
    @Override
    public boolean updateUserInfo(User user) {
        User existingUser = this.getById(user.getUserId());
        if (existingUser == null) {
            return false;
        }
        
        // 如果密码不为空且已改变，则加密
        if (user.getPassword() != null && !user.getPassword().isEmpty() 
            && !user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }
        
        return this.updateById(user);
    }
    
    @Override
    public List<User> getAllUsers() {
        return this.list();
    }
}

