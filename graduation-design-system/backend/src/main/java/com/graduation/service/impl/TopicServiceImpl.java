package com.graduation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graduation.entity.Topic;
import com.graduation.mapper.TopicMapper;
import com.graduation.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 毕设题目服务实现类
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    
    @Autowired
    private TopicMapper topicMapper;
    
    @Override
    public List<Topic> findByTeacherId(Integer teacherId) {
        return lambdaQuery().eq(Topic::getTeacherId, teacherId).list();
    }
    
    @Override
    public List<Topic> findByType(Integer type) {
        return lambdaQuery().eq(Topic::getType, type).list();
    }
    
    @Override
    public List<Topic> findByStatus(Integer status) {
        return lambdaQuery().eq(Topic::getStatus, status).list();
    }
    
    @Override
    public boolean updateSelectedCount(Integer topicId, int increment) {
        Topic topic = getById(topicId);
        if (topic != null) {
            int newCount = topic.getSelectedCount() + increment;
            if (newCount >= 0) {
                topic.setSelectedCount(newCount);
                return updateById(topic);
            }
        }
        return false;
    }
    
    @Override
    public boolean publishTopic(Integer topicId) {
        Topic topic = getById(topicId);
        if (topic != null) {
            topic.setStatus(1); // 1表示发布中
            return updateById(topic);
        }
        return false;
    }
}
