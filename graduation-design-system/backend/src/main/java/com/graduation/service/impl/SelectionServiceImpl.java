package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.Selection;
import com.graduation.entity.Topic;
import com.graduation.mapper.SelectionMapper;
import com.graduation.service.SelectionService;
import com.graduation.service.TopicService;
import com.graduation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生选题服务实现类
 */
@Service
public class SelectionServiceImpl extends ServiceImpl<SelectionMapper, Selection> implements SelectionService {
    
    @Autowired
    private SelectionMapper selectionMapper;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Override
    public List<Selection> findByStudentId(Integer studentId) {
        return lambdaQuery().eq(Selection::getStudentId, studentId).list();
    }
    
    @Override
    public List<Selection> findByTeacherId(Integer teacherId) {
        return lambdaQuery().eq(Selection::getTeacherId, teacherId).list();
    }
    
    @Override
    public List<Selection> findByTopicId(Integer topicId) {
        return lambdaQuery().eq(Selection::getTopicId, topicId).list();
    }
    
    @Override
    public List<Selection> findByStatus(Integer status) {
        return lambdaQuery().eq(Selection::getStatus, status).list();
    }
    
    @Override
    @Transactional
    public boolean approveSelection(Integer selectionId, Integer status, String comment) {
        Selection selection = getById(selectionId);
        if (selection != null) {
            selection.setStatus(status);
            selection.setApprovalComment(comment);
            selection.setApprovalTime(LocalDateTime.now());
            
            boolean result = updateById(selection);
            
            // 如果是同意申请
            if (result && status == 1) {
                // 如果是选择已有题目
                if (selection.getSelectionType() == 0 && selection.getTopicId() != null) {
                    // 增加题目已选人数
                    topicService.updateSelectedCount(selection.getTopicId(), 1);
                }
                
                // 增加教师指导学生数量
                if (selection.getTeacherId() != null) {
                    teacherService.updateStudentCount(selection.getTeacherId(), 1);
                }
            }
            
            return result;
        }
        return false;
    }
    
    @Override
    public boolean applySelection(Selection selection) {
        // 检查学生是否已经有有效的选题
        List<Selection> existingSelections = lambdaQuery()
                .eq(Selection::getStudentId, selection.getStudentId())
                .eq(Selection::getStatus, 1) // 已同意的选题
                .list();
        
        if (!existingSelections.isEmpty()) {
            return false; // 学生已有有效的选题，不允许重复申请
        }
        
        // 如果是选择已有题目
        if (selection.getSelectionType() == 0 && selection.getTopicId() != null) {
            Topic topic = topicService.getById(selection.getTopicId());
            if (topic == null || topic.getStatus() != 1) {
                return false; // 题目不存在或未发布
            }
            
            // 检查题目是否已满
            if (topic.getSelectedCount() >= topic.getMaxStudents()) {
                return false; // 题目已选满
            }
            
            // 设置教师ID
            selection.setTeacherId(topic.getTeacherId());
        }
        
        selection.setStatus(0); // 初始状态为待审批
        selection.setApplyTime(LocalDateTime.now());
        
        return save(selection);
    }
}
