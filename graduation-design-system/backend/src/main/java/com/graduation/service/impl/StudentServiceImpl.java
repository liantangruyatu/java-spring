package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.Student;
import com.graduation.mapper.StudentMapper;
import com.graduation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    public Student findByUserId(Integer userId) {
        return lambdaQuery().eq(Student::getUserId, userId).one();
    }
    
    @Override
    public Student findByStudentNo(String studentNo) {
        return lambdaQuery().eq(Student::getStudentNo, studentNo).one();
    }
    
    @Override
    public List<Student> findByClassName(String className) {
        return lambdaQuery().eq(Student::getClassName, className).list();
    }
}
