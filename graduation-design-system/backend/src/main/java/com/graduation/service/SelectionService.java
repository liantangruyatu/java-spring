package com.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Selection;

import java.util.List;

/**
 * 学生选题服务接口
 */
public interface SelectionService extends IService<Selection> {
    
    /**
     * 根据学生ID查找选题
     * @param studentId 学生ID
     * @return 选题列表
     */
    List<Selection> findByStudentId(Integer studentId);
    
    /**
     * 根据教师ID查找选题
     * @param teacherId 教师ID
     * @return 选题列表
     */
    List<Selection> findByTeacherId(Integer teacherId);
    
    /**
     * 根据题目ID查找选题
     * @param topicId 题目ID
     * @return 选题列表
     */
    List<Selection> findByTopicId(Integer topicId);
    
    /**
     * 根据状态查找选题
     * @param status 状态
     * @return 选题列表
     */
    List<Selection> findByStatus(Integer status);
    
    /**
     * 审批选题申请
     * @param selectionId 选题ID
     * @param status 审批状态
     * @param comment 审批意见
     * @return 是否审批成功
     */
    boolean approveSelection(Integer selectionId, Integer status, String comment);
    
    /**
     * 申请选题
     * @param selection 选题信息
     * @return 是否申请成功
     */
    boolean applySelection(Selection selection);
}
