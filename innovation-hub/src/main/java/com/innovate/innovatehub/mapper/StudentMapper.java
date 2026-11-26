package com.innovate.innovatehub.mapper;

import com.innovate.innovatehub.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 学生Mapper接口
 */
@Mapper
public interface StudentMapper {
    
    @Select("SELECT * FROM student")
    List<Student> findAll();
    
    @Select("SELECT * FROM student WHERE student_id = #{studentId}")
    Student findById(Integer studentId);
    
    @Select("SELECT * FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Student> findByName(String name);
    
    @Select("SELECT * FROM student WHERE username = #{username}")
    Student findByUsername(String username);
    
    @Insert("INSERT INTO student(username, password, name, major, class_name, phone, email) VALUES(#{username}, #{password}, #{name}, #{major}, #{className}, #{phone}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId")
    int insert(Student student);
    
    @Update("UPDATE student SET username=#{username}, password=#{password}, name=#{name}, major=#{major}, class_name=#{className}, phone=#{phone}, email=#{email} WHERE student_id=#{studentId}")
    int update(Student student);
    
    @Delete("DELETE FROM student WHERE student_id = #{studentId}")
    int delete(Integer studentId);
}
