package com.innovate.innovatehub.controller;

import com.innovate.innovatehub.entity.Student;
import com.innovate.innovatehub.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生控制器
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public Map<String, Object> getAllStudents() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Student> students = studentService.getAllStudents();
            result.put("success", true);
            result.put("data", students);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getStudentById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Student student = studentService.getStudentById(id);
            result.put("success", true);
            result.put("data", student);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addStudent(@RequestBody Student student) {
        Map<String, Object> result = new HashMap<>();
        try {
            studentService.addStudent(student);
            result.put("success", true);
            result.put("message", "学生添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        Map<String, Object> result = new HashMap<>();
        try {
            student.setStudentId(id);
            studentService.updateStudent(student);
            result.put("success", true);
            result.put("message", "学生更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteStudent(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            studentService.deleteStudent(id);
            result.put("success", true);
            result.put("message", "学生删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
