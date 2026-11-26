package com.innovate.innovatehub.mapper;

import com.innovate.innovatehub.entity.Selection;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 选题记录Mapper接口
 */
@Mapper
public interface SelectionMapper {
    
    @Select("SELECT s.*, student.name as studentName, topic.title as topicTitle, teacher.name as teacherName " +
            "FROM selection s " +
            "LEFT JOIN student ON s.student_id = student.student_id " +
            "LEFT JOIN topic ON s.topic_id = topic.topic_id " +
            "LEFT JOIN teacher ON topic.teacher_id = teacher.teacher_id")
    List<Selection> findAll();
    
    @Select("SELECT s.*, student.name as studentName, topic.title as topicTitle, teacher.name as teacherName " +
            "FROM selection s " +
            "LEFT JOIN student ON s.student_id = student.student_id " +
            "LEFT JOIN topic ON s.topic_id = topic.topic_id " +
            "LEFT JOIN teacher ON topic.teacher_id = teacher.teacher_id " +
            "WHERE s.selection_id = #{selectionId}")
    Selection findById(Integer selectionId);
    
    @Select("SELECT s.*, student.name as studentName, topic.title as topicTitle, teacher.name as teacherName " +
            "FROM selection s " +
            "LEFT JOIN student ON s.student_id = student.student_id " +
            "LEFT JOIN topic ON s.topic_id = topic.topic_id " +
            "LEFT JOIN teacher ON topic.teacher_id = teacher.teacher_id " +
            "WHERE s.student_id = #{studentId}")
    List<Selection> findByStudentId(Integer studentId);
    
    @Insert("INSERT INTO selection(student_id, topic_id, status) " +
            "VALUES(#{studentId}, #{topicId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "selectionId")
    int insert(Selection selection);
    
    @Update("UPDATE selection SET student_id=#{studentId}, topic_id=#{topicId}, status=#{status} WHERE selection_id=#{selectionId}")
    int update(Selection selection);
    
    @Delete("DELETE FROM selection WHERE selection_id = #{selectionId}")
    int delete(Integer selectionId);
}
