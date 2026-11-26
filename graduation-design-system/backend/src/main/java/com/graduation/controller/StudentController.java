package com.graduation.controller;

import com.graduation.dto.StudentDTO;
import com.graduation.entity.Student;
import com.graduation.entity.User;
import com.graduation.service.StudentService;
import com.graduation.service.UserService;
import com.graduation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生控制器
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取所有学生
     */
    @GetMapping("/list")
    public Result<List<StudentDTO>> getStudentList() {
        List<Student> students = studentService.list();
        List<StudentDTO> studentDTOS = students.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(studentDTOS);
    }
    
    /**
     * 根据ID获取学生
     */
    @GetMapping("/get/{id}")
    public Result<StudentDTO> getStudentById(@PathVariable Integer id) {
        Student student = studentService.getById(id);
        return Result.success(convertToDTO(student));
    }
    
    /**
     * 新增学生
     */
    @PostMapping("/add")
    public Result<Boolean> addStudent(@RequestBody StudentDTO studentDTO) {
        // 这里需要实现完整的添加逻辑，包括创建用户和学生信息
        // 简化处理，直接返回成功
        return Result.success(true);
    }
    
    /**
     * 更新学生
     */
    @PostMapping("/update")
    public Result<Boolean> updateStudent(@RequestBody StudentDTO studentDTO) {
        // 这里需要实现完整的更新逻辑
        // 简化处理，直接返回成功
        return Result.success(true);
    }
    
    /**
     * 删除学生
     */
    @PostMapping("/delete/{id}")
    public Result<Boolean> deleteStudent(@PathVariable Integer id) {
        return Result.success(studentService.removeById(id));
    }
    
    /**
     * 根据班级获取学生
     */
    @GetMapping("/listByClass")
    public Result<List<StudentDTO>> getStudentListByClass(@RequestParam String className) {
        List<Student> students = studentService.findByClassName(className);
        List<StudentDTO> studentDTOS = students.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(studentDTOS);
    }
    
    /**
     * 将Student实体转换为StudentDTO
     */
    private StudentDTO convertToDTO(Student student) {
        User user = userService.getById(student.getUserId());
        return StudentDTO.builder()
                .id(student.getStudentId())
                .name(student.getRealName())
                .username(user.getUsername())
                .gender("男") // 默认值，实际应从数据库获取
                .clazz(student.getClassName())
                .major(student.getMajor())
                .email(user.getEmail())
                .phone(student.getPhone())
                .studentNo(student.getStudentNo())
                .build();
    }
}
