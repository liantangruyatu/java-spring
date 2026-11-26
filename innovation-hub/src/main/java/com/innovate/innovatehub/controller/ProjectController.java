package com.innovate.innovatehub.controller;

import com.innovate.innovatehub.entity.Project;
import com.innovate.innovatehub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目控制器
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public Map<String, Object> getAllProjects() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Project> projects = projectService.getAllProjects();
            result.put("success", true);
            result.put("data", projects);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getProjectById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Project project = projectService.getProjectById(id);
            result.put("success", true);
            result.put("data", project);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search/name")
    public Map<String, Object> searchProjectsByName(@RequestParam String projectName) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Project> projects = projectService.getProjectsByName(projectName);
            result.put("success", true);
            result.put("data", projects);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search/teacher/{teacherId}")
    public Map<String, Object> searchProjectsByTeacher(@PathVariable Integer teacherId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Project> projects = projectService.getProjectsByTeacherId(teacherId);
            result.put("success", true);
            result.put("data", projects);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/search/student/{studentId}")
    public Map<String, Object> searchProjectsByStudent(@PathVariable Integer studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Project> projects = projectService.getProjectsByStudentId(studentId);
            result.put("success", true);
            result.put("data", projects);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addProject(@RequestBody Project project) {
        Map<String, Object> result = new HashMap<>();
        try {
            projectService.addProject(project);
            result.put("success", true);
            result.put("message", "项目添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateProject(@PathVariable Integer id, @RequestBody Project project) {
        Map<String, Object> result = new HashMap<>();
        try {
            project.setProjectId(id);
            projectService.updateProject(project);
            result.put("success", true);
            result.put("message", "项目更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProject(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            projectService.deleteProject(id);
            result.put("success", true);
            result.put("message", "项目删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
