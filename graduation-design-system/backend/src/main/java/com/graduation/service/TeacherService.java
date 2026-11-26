package com.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Teacher;

import java.util.List;

/**
 * 教师服务接口
 */
public interface TeacherService extends IService<Teacher> {
    
    /**
     * 根据用户ID查找教师
     * @param userId 用户ID
     * @return 教师信息
     */
    Teacher findByUserId(Integer userId);
    
    /**
     * 根据部门查找教师
     * @param department 部门
     * @return 教师列表
     */
    List<Teacher> findByDepartment(String department);
    
    /**
     * 更新教师指导学生数量
     * @param teacherId 教师ID
     * @param increment 增加或减少的数量
     * @return 是否更新成功
     */
    boolean updateStudentCount(Integer teacherId, int increment);
}
