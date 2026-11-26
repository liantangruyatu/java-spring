package com.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Student;

/**
 * 学生服务接口
 */
public interface StudentService extends IService<Student> {
    
    /**
     * 根据用户ID查找学生
     * @param userId 用户ID
     * @return 学生信息
     */
    Student findByUserId(Integer userId);
    
    /**
     * 根据学号查找学生
     * @param studentNo 学号
     * @return 学生信息
     */
    Student findByStudentNo(String studentNo);
    
    /**
     * 根据班级查找学生
     * @param className 班级
     * @return 学生列表
     */
    java.util.List<Student> findByClassName(String className);
}
