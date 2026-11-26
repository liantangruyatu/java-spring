package com.innovate.innovatehub.service;

import com.innovate.innovatehub.entity.Student;
import com.innovate.innovatehub.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务实现类
 */
@Service
public class StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }
    
    public Student getStudentById(Integer studentId) {
        return studentMapper.findById(studentId);
    }
    
    public List<Student> getStudentsByName(String name) {
        return studentMapper.findByName(name);
    }
    
    public int addStudent(Student student) {
        return studentMapper.insert(student);
    }
    
    public int updateStudent(Student student) {
        return studentMapper.update(student);
    }
    
    public int deleteStudent(Integer studentId) {
        return studentMapper.delete(studentId);
    }
}
