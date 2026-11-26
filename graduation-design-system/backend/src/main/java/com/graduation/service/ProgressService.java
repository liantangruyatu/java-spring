package com.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Progress;

import java.util.List;

/**
 * 毕设进度服务接口
 */
public interface ProgressService extends IService<Progress> {
    
    /**
     * 根据学生ID查找进度
     * @param studentId 学生ID
     * @return 进度列表
     */
    List<Progress> findByStudentId(Integer studentId);
    
    /**
     * 根据教师ID查找进度
     * @param teacherId 教师ID
     * @return 进度列表
     */
    List<Progress> findByTeacherId(Integer teacherId);
    
    /**
     * 根据选题ID查找进度
     * @param selectionId 选题ID
     * @return 进度列表
     */
    List<Progress> findBySelectionId(Integer selectionId);
    
    /**
     * 根据状态查找进度
     * @param status 状态
     * @return 进度列表
     */
    List<Progress> findByStatus(Integer status);
    
    /**
     * 更新进度状态
     * @param progressId 进度ID
     * @param status 状态
     * @param comment 评价
     * @return 是否更新成功
     */
    boolean updateProgressStatus(Integer progressId, Integer status, String comment);
}
