package com.innovate.innovatehub.service;

import com.innovate.innovatehub.entity.Teacher;
import com.innovate.innovatehub.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师服务实现类
 */
@Service
public class TeacherService {
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAll();
    }
    
    public Teacher getTeacherById(Integer teacherId) {
        return teacherMapper.findById(teacherId);
    }
    
    public List<Teacher> getTeachersByName(String name) {
        return teacherMapper.findByName(name);
    }
    
    public int addTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }
    
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.update(teacher);
    }
    
    public int deleteTeacher(Integer teacherId) {
        return teacherMapper.delete(teacherId);
    }
}
