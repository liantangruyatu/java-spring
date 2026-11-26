package com.graduation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.graduation.entity.Topic;

import java.util.List;

/**
 * 毕设题目服务接口
 */
public interface TopicService extends IService<Topic> {
    
    /**
     * 根据教师ID查找题目
     * @param teacherId 教师ID
     * @return 题目列表
     */
    List<Topic> findByTeacherId(Integer teacherId);
    
    /**
     * 根据题目类型查找题目
     * @param type 题目类型
     * @return 题目列表
     */
    List<Topic> findByType(Integer type);
    
    /**
     * 根据状态查找题目
     * @param status 状态
     * @return 题目列表
     */
    List<Topic> findByStatus(Integer status);
    
    /**
     * 更新题目已选人数
     * @param topicId 题目ID
     * @param increment 增加或减少的数量
     * @return 是否更新成功
     */
    boolean updateSelectedCount(Integer topicId, int increment);
    
    /**
     * 发布题目
     * @param topicId 题目ID
     * @return 是否发布成功
     */
    boolean publishTopic(Integer topicId);
}
