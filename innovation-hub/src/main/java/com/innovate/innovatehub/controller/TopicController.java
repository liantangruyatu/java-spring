package com.innovate.innovatehub.controller;

import com.innovate.innovatehub.entity.Topic;
import com.innovate.innovatehub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目控制器
 */
@RestController
@RequestMapping("/api/topics")
@CrossOrigin
public class TopicController {
    
    @Autowired
    private TopicService topicService;
    
    @GetMapping
    public Map<String, Object> getAllTopics() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Topic> topics = topicService.getAllTopics();
            result.put("success", true);
            result.put("data", topics);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getTopicById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Topic topic = topicService.getTopicById(id);
            result.put("success", true);
            result.put("data", topic);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/teacher/{teacherId}")
    public Map<String, Object> getTopicsByTeacher(@PathVariable Integer teacherId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Topic> topics = topicService.getTopicsByTeacherId(teacherId);
            result.put("success", true);
            result.put("data", topics);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search")
    public Map<String, Object> searchTopicsByTitle(@RequestParam String title) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Topic> topics = topicService.getTopicsByTitle(title);
            result.put("success", true);
            result.put("data", topics);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addTopic(@RequestBody Topic topic) {
        Map<String, Object> result = new HashMap<>();
        try {
            topicService.addTopic(topic);
            result.put("success", true);
            result.put("message", "题目发布成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateTopic(@PathVariable Integer id, @RequestBody Topic topic) {
        Map<String, Object> result = new HashMap<>();
        try {
            topic.setTopicId(id);
            topicService.updateTopic(topic);
            result.put("success", true);
            result.put("message", "题目更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteTopic(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            topicService.deleteTopic(id);
            result.put("success", true);
            result.put("message", "题目删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
