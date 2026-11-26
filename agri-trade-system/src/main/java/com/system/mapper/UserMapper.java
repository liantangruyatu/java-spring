package com.system.mapper;

import com.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    
    User findById(@Param("id") Integer id);
    
    int insert(User user);
    
    int update(User user);
    
    int deleteById(@Param("id") Integer id);
    
    List<User> findAll();
    
    List<User> findByRole(@Param("role") String role);
    
    User findByEmail(@Param("email") String email);
}