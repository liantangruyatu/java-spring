package com.innovate.innovatehub.mapper;

import com.innovate.innovatehub.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 教师Mapper接口
 */
@Mapper
public interface TeacherMapper {
    
    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();
    
    @Select("SELECT * FROM teacher WHERE teacher_id = #{teacherId}")
    Teacher findById(Integer teacherId);
    
    @Select("SELECT * FROM teacher WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Teacher> findByName(String name);
    
    @Select("SELECT * FROM teacher WHERE username = #{username}")
    Teacher findByUsername(String username);
    
    @Insert("INSERT INTO teacher(username, password, name, college, title, phone, email) VALUES(#{username}, #{password}, #{name}, #{college}, #{title}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "teacherId")
    int insert(Teacher teacher);
    
    @Update("UPDATE teacher SET username=#{username}, password=#{password}, name=#{name}, college=#{college}, title=#{title}, phone=#{phone}, email=#{email} WHERE teacher_id=#{teacherId}")
    int update(Teacher teacher);
    
    @Delete("DELETE FROM teacher WHERE teacher_id = #{teacherId}")
    int delete(Integer teacherId);
}
