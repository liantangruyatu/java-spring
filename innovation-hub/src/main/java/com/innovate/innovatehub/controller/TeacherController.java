package com.innovate.innovatehub.controller;

import com.innovate.innovatehub.entity.Teacher;
import com.innovate.innovatehub.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师控制器
 */
@RestController
@RequestMapping("/api/teachers")
@CrossOrigin
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;
    
    @GetMapping
    public Map<String, Object> getAllTeachers() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Teacher> teachers = teacherService.getAllTeachers();
            result.put("success", true);
            result.put("data", teachers);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getTeacherById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            result.put("success", true);
            result.put("data", teacher);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search")
    public Map<String, Object> searchTeachersByName(@RequestParam String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Teacher> teachers = teacherService.getTeachersByName(name);
            result.put("success", true);
            result.put("data", teachers);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addTeacher(@RequestBody Teacher teacher) {
        Map<String, Object> result = new HashMap<>();
        try {
            teacherService.addTeacher(teacher);
            result.put("success", true);
            result.put("message", "教师添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateTeacher(@PathVariable Integer id, @RequestBody Teacher teacher) {
        Map<String, Object> result = new HashMap<>();
        try {
            teacher.setTeacherId(id);
            teacherService.updateTeacher(teacher);
            result.put("success", true);
            result.put("message", "教师更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteTeacher(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            teacherService.deleteTeacher(id);
            result.put("success", true);
            result.put("message", "教师删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
