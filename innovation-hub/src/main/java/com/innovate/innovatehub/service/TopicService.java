package com.innovate.innovatehub.service;

import com.innovate.innovatehub.entity.Topic;
import com.innovate.innovatehub.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题目服务实现类
 */
@Service
public class TopicService {
    
    @Autowired
    private TopicMapper topicMapper;
    
    public List<Topic> getAllTopics() {
        return topicMapper.findAll();
    }
    
    public Topic getTopicById(Integer topicId) {
        return topicMapper.findById(topicId);
    }
    
    public List<Topic> getTopicsByTeacherId(Integer teacherId) {
        return topicMapper.findByTeacherId(teacherId);
    }
    
    public List<Topic> getTopicsByTitle(String title) {
        return topicMapper.findByTitle(title);
    }
    
    public int addTopic(Topic topic) {
        return topicMapper.insert(topic);
    }
    
    public int updateTopic(Topic topic) {
        return topicMapper.update(topic);
    }
    
    public int deleteTopic(Integer topicId) {
        return topicMapper.delete(topicId);
    }
}
