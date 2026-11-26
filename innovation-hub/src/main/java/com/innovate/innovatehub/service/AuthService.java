package com.innovate.innovatehub.service;

import com.innovate.innovatehub.dto.LoginRequest;
import com.innovate.innovatehub.dto.RegisterRequest;
import com.innovate.innovatehub.entity.Student;
import com.innovate.innovatehub.entity.Teacher;
import com.innovate.innovatehub.mapper.StudentMapper;
import com.innovate.innovatehub.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务
 */
@Service
public class AuthService {
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * 用户登录
     */
    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        if ("teacher".equals(request.getUserType())) {
            Teacher teacher = teacherMapper.findByUsername(request.getUsername());
            if (teacher == null) {
                result.put("success", false);
                result.put("message", "用户名不存在");
                return result;
            }
            if (!teacher.getPassword().equals(request.getPassword())) {
                result.put("success", false);
                result.put("message", "密码错误");
                return result;
            }
            // 不返回密码
            teacher.setPassword(null);
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("userType", "teacher");
            result.put("user", teacher);
            
        } else if ("student".equals(request.getUserType())) {
            Student student = studentMapper.findByUsername(request.getUsername());
            if (student == null) {
                result.put("success", false);
                result.put("message", "用户名不存在");
                return result;
            }
            if (!student.getPassword().equals(request.getPassword())) {
                result.put("success", false);
                result.put("message", "密码错误");
                return result;
            }
            // 不返回密码
            student.setPassword(null);
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("userType", "student");
            result.put("user", student);
            
        } else {
            result.put("success", false);
            result.put("message", "用户类型错误");
        }
        
        return result;
    }
    
    /**
     * 用户注册
     */
    public Map<String, Object> register(RegisterRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        if ("teacher".equals(request.getUserType())) {
            // 检查用户名是否存在
            if (teacherMapper.findByUsername(request.getUsername()) != null) {
                result.put("success", false);
                result.put("message", "用户名已存在");
                return result;
            }
            
            Teacher teacher = new Teacher();
            teacher.setUsername(request.getUsername());
            teacher.setPassword(request.getPassword());
            teacher.setName(request.getName());
            teacher.setCollege(request.getCollege());
            teacher.setTitle(request.getTitle());
            teacher.setPhone(request.getPhone());
            teacher.setEmail(request.getEmail());
            
            teacherMapper.insert(teacher);
            result.put("success", true);
            result.put("message", "注册成功");
            
        } else if ("student".equals(request.getUserType())) {
            // 检查用户名是否存在
            if (studentMapper.findByUsername(request.getUsername()) != null) {
                result.put("success", false);
                result.put("message", "用户名已存在");
                return result;
            }
            
            Student student = new Student();
            student.setUsername(request.getUsername());
            student.setPassword(request.getPassword());
            student.setName(request.getName());
            student.setMajor(request.getMajor());
            student.setClassName(request.getClassName());
            student.setPhone(request.getPhone());
            student.setEmail(request.getEmail());
            
            studentMapper.insert(student);
            result.put("success", true);
            result.put("message", "注册成功");
            
        } else {
            result.put("success", false);
            result.put("message", "用户类型错误");
        }
        
        return result;
    }
}
