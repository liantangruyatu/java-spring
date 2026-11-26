package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.Teacher;
import com.graduation.mapper.TeacherMapper;
import com.graduation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师服务实现类
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Override
    public Teacher findByUserId(Integer userId) {
        return lambdaQuery().eq(Teacher::getUserId, userId).one();
    }
    
    @Override
    public List<Teacher> findByDepartment(String department) {
        return lambdaQuery().eq(Teacher::getDepartment, department).list();
    }
    
    @Override
    public boolean updateStudentCount(Integer teacherId, int increment) {
        Teacher teacher = getById(teacherId);
        if (teacher != null) {
            int newCount = teacher.getCurrentCount() + increment;
            if (newCount >= 0) {
                teacher.setCurrentCount(newCount);
                return updateById(teacher);
            }
        }
        return false;
    }
}
