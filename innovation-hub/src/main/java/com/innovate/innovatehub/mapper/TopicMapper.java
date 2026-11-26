package com.innovate.innovatehub.mapper;

import com.innovate.innovatehub.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 题目Mapper接口
 */
@Mapper
public interface TopicMapper {
    
    @Select("SELECT t.*, teacher.name as teacherName " +
            "FROM topic t " +
            "LEFT JOIN teacher ON t.teacher_id = teacher.teacher_id")
    List<Topic> findAll();
    
    @Select("SELECT t.*, teacher.name as teacherName " +
            "FROM topic t " +
            "LEFT JOIN teacher ON t.teacher_id = teacher.teacher_id " +
            "WHERE t.topic_id = #{topicId}")
    Topic findById(Integer topicId);
    
    @Select("SELECT t.*, teacher.name as teacherName " +
            "FROM topic t " +
            "LEFT JOIN teacher ON t.teacher_id = teacher.teacher_id " +
            "WHERE t.teacher_id = #{teacherId}")
    List<Topic> findByTeacherId(Integer teacherId);
    
    @Select("SELECT t.*, teacher.name as teacherName " +
            "FROM topic t " +
            "LEFT JOIN teacher ON t.teacher_id = teacher.teacher_id " +
            "WHERE t.title LIKE CONCAT('%', #{title}, '%')")
    List<Topic> findByTitle(String title);
    
    @Insert("INSERT INTO topic(title, description, teacher_id) " +
            "VALUES(#{title}, #{description}, #{teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "topicId")
    int insert(Topic topic);
    
    @Update("UPDATE topic SET title=#{title}, description=#{description}, teacher_id=#{teacherId} WHERE topic_id=#{topicId}")
    int update(Topic topic);
    
    @Delete("DELETE FROM topic WHERE topic_id = #{topicId}")
    int delete(Integer topicId);
}
