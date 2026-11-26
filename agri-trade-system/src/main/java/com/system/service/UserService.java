package com.system.service;

import com.system.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    
    User register(User user);
    
    User findById(Integer id);
    
    User findByUsername(String username);
    
    User update(User user);
    
    boolean deleteById(Integer id);
    
    List<User> findAll();
    
    List<User> findByRole(String role);
    
    boolean changePassword(Integer userId, String oldPassword, String newPassword);
}