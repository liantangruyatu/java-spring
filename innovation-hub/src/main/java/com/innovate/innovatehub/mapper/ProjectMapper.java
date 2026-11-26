package com.innovate.innovatehub.mapper;

import com.innovate.innovatehub.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 项目Mapper接口
 */
@Mapper
public interface ProjectMapper {
    
    @Select("SELECT p.*, t.name as teacherName, s.name as studentName " +
            "FROM project p " +
            "LEFT JOIN teacher t ON p.teacher_id = t.teacher_id " +
            "LEFT JOIN student s ON p.student_id = s.student_id")
    List<Project> findAll();
    
    @Select("SELECT p.*, t.name as teacherName, s.name as studentName " +
            "FROM project p " +
            "LEFT JOIN teacher t ON p.teacher_id = t.teacher_id " +
            "LEFT JOIN student s ON p.student_id = s.student_id " +
            "WHERE p.project_id = #{projectId}")
    Project findById(Integer projectId);
    
    @Select("SELECT p.*, t.name as teacherName, s.name as studentName " +
            "FROM project p " +
            "LEFT JOIN teacher t ON p.teacher_id = t.teacher_id " +
            "LEFT JOIN student s ON p.student_id = s.student_id " +
            "WHERE p.project_name LIKE CONCAT('%', #{projectName}, '%')")
    List<Project> findByProjectName(String projectName);
    
    @Select("SELECT p.*, t.name as teacherName, s.name as studentName " +
            "FROM project p " +
            "LEFT JOIN teacher t ON p.teacher_id = t.teacher_id " +
            "LEFT JOIN student s ON p.student_id = s.student_id " +
            "WHERE p.teacher_id = #{teacherId}")
    List<Project> findByTeacherId(Integer teacherId);
    
    @Select("SELECT p.*, t.name as teacherName, s.name as studentName " +
            "FROM project p " +
            "LEFT JOIN teacher t ON p.teacher_id = t.teacher_id " +
            "LEFT JOIN student s ON p.student_id = s.student_id " +
            "WHERE p.student_id = #{studentId}")
    List<Project> findByStudentId(Integer studentId);
    
    @Insert("INSERT INTO project(project_name, description, teacher_id, student_id, status) " +
            "VALUES(#{projectName}, #{description}, #{teacherId}, #{studentId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "projectId")
    int insert(Project project);
    
    @Update("UPDATE project SET project_name=#{projectName}, description=#{description}, " +
            "teacher_id=#{teacherId}, student_id=#{studentId}, status=#{status} WHERE project_id=#{projectId}")
    int update(Project project);
    
    @Delete("DELETE FROM project WHERE project_id = #{projectId}")
    int delete(Integer projectId);
}
