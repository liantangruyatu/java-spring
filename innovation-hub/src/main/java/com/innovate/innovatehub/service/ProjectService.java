package com.innovate.innovatehub.service;

import com.innovate.innovatehub.entity.Project;
import com.innovate.innovatehub.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目服务实现类
 */
@Service
public class ProjectService {
    
    @Autowired
    private ProjectMapper projectMapper;
    
    public List<Project> getAllProjects() {
        return projectMapper.findAll();
    }
    
    public Project getProjectById(Integer projectId) {
        return projectMapper.findById(projectId);
    }
    
    public List<Project> getProjectsByName(String projectName) {
        return projectMapper.findByProjectName(projectName);
    }
    
    public List<Project> getProjectsByTeacherId(Integer teacherId) {
        return projectMapper.findByTeacherId(teacherId);
    }
    
    public List<Project> getProjectsByStudentId(Integer studentId) {
        return projectMapper.findByStudentId(studentId);
    }
    
    public int addProject(Project project) {
        if (project.getStatus() == null || project.getStatus().isEmpty()) {
            project.setStatus("待审核");
        }
        return projectMapper.insert(project);
    }
    
    public int updateProject(Project project) {
        return projectMapper.update(project);
    }
    
    public int deleteProject(Integer projectId) {
        return projectMapper.delete(projectId);
    }
}
