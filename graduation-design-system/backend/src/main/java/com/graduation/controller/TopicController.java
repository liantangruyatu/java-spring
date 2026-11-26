package com.graduation.controller;

import com.graduation.entity.Topic;
import com.graduation.service.TopicService;
import com.graduation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 毕设题目控制器
 */
@RestController
@RequestMapping("/topic")
public class TopicController {
    
    @Autowired
    private TopicService topicService;
    
    /**
     * 获取所有题目
     */
    @GetMapping("/list")
    public Result<List<Topic>> getTopicList() {
        return Result.success(topicService.list());
    }
    
    /**
     * 根据ID获取题目
     */
    @GetMapping("/get/{id}")
    public Result<Topic> getTopicById(@PathVariable Integer id) {
        return Result.success(topicService.getById(id));
    }
    
    /**
     * 新增题目
     */
    @PostMapping("/add")
    public Result<Boolean> addTopic(@RequestBody Topic topic) {
        return Result.success(topicService.save(topic));
    }
    
    /**
     * 更新题目
     */
    @PostMapping("/update")
    public Result<Boolean> updateTopic(@RequestBody Topic topic) {
        return Result.success(topicService.updateById(topic));
    }
    
    /**
     * 删除题目
     */
    @PostMapping("/delete/{id}")
    public Result<Boolean> deleteTopic(@PathVariable Integer id) {
        return Result.success(topicService.removeById(id));
    }
    
    /**
     * 根据教师ID获取题目
     */
    @GetMapping("/listByTeacher")
    public Result<List<Topic>> getTopicListByTeacher(@RequestParam Integer teacherId) {
        return Result.success(topicService.findByTeacherId(teacherId));
    }
    
    /**
     * 根据类型获取题目
     */
    @GetMapping("/listByType")
    public Result<List<Topic>> getTopicListByType(@RequestParam Integer type) {
        return Result.success(topicService.findByType(type));
    }
    
    /**
     * 根据状态获取题目
     */
    @GetMapping("/listByStatus")
    public Result<List<Topic>> getTopicListByStatus(@RequestParam Integer status) {
        return Result.success(topicService.findByStatus(status));
    }
    
    /**
     * 发布题目
     */
    @PostMapping("/publish/{id}")
    public Result<Boolean> publishTopic(@PathVariable Integer id) {
        return Result.success(topicService.publishTopic(id));
    }
}
