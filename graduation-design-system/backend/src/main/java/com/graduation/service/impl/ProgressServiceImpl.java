package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.Progress;
import com.graduation.mapper.ProgressMapper;
import com.graduation.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 毕设进度服务实现类
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements ProgressService {
    
    @Autowired
    private ProgressMapper progressMapper;
    
    @Override
    public List<Progress> findByStudentId(Integer studentId) {
        // 需要通过selection表关联查询，暂时返回空列表
        return List.of();
    }
    
    @Override
    public List<Progress> findByTeacherId(Integer teacherId) {
        // 需要通过selection表关联查询，暂时返回空列表
        return List.of();
    }
    
    @Override
    public List<Progress> findBySelectionId(Integer selectionId) {
        return lambdaQuery().eq(Progress::getSelectionId, selectionId).list();
    }
    
    @Override
    public List<Progress> findByStatus(Integer status) {
        return lambdaQuery().eq(Progress::getStatus, status).list();
    }
    
    @Override
    public boolean updateProgressStatus(Integer progressId, Integer status, String comment) {
        Progress progress = getById(progressId);
        if (progress != null) {
            progress.setStatus(status);
            progress.setTeacherComment(comment);
            progress.setUpdateTime(LocalDateTime.now());
            return updateById(progress);
        }
        return false;
    }
}
