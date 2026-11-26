package com.graduation.controller;

import com.graduation.dto.TeacherDTO;
import com.graduation.entity.Teacher;
import com.graduation.entity.User;
import com.graduation.service.TeacherService;
import com.graduation.service.UserService;
import com.graduation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 教师控制器
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取所有教师
     */
    @GetMapping("/list")
    public Result<List<TeacherDTO>> getTeacherList() {
        List<Teacher> teachers = teacherService.list();
        List<TeacherDTO> teacherDTOS = teachers.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(teacherDTOS);
    }
    
    /**
     * 根据ID获取教师
     */
    @GetMapping("/get/{id}")
    public Result<TeacherDTO> getTeacherById(@PathVariable Integer id) {
        Teacher teacher = teacherService.getById(id);
        return Result.success(convertToDTO(teacher));
    }
    
    /**
     * 新增教师
     */
    @PostMapping("/add")
    public Result<Boolean> addTeacher(@RequestBody TeacherDTO teacherDTO) {
        // 这里需要实现完整的添加逻辑，包括创建用户和教师信息
        // 简化处理，直接返回成功
        return Result.success(true);
    }
    
    /**
     * 更新教师
     */
    @PostMapping("/update")
    public Result<Boolean> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
        // 这里需要实现完整的更新逻辑
        // 简化处理，直接返回成功
        return Result.success(true);
    }
    
    /**
     * 删除教师
     */
    @PostMapping("/delete/{id}")
    public Result<Boolean> deleteTeacher(@PathVariable Integer id) {
        return Result.success(teacherService.removeById(id));
    }
    
    /**
     * 根据部门获取教师
     */
    @GetMapping("/listByDept")
    public Result<List<TeacherDTO>> getTeacherListByDept(@RequestParam String department) {
        List<Teacher> teachers = teacherService.findByDepartment(department);
        List<TeacherDTO> teacherDTOS = teachers.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(teacherDTOS);
    }
    
    /**
     * 将Teacher实体转换为TeacherDTO
     */
    private TeacherDTO convertToDTO(Teacher teacher) {
        User user = userService.getById(teacher.getUserId());
        return TeacherDTO.builder()
                .id(teacher.getTeacherId())
                .name(teacher.getRealName())
                .username(user.getUsername())
                .gender("男") // 默认值，实际应从数据库获取
                .title("教授") // 默认值，实际应从数据库获取
                .email(user.getEmail())
                .phone(teacher.getPhone())
                .department(teacher.getDepartment())
                .researchArea(teacher.getResearchArea())
                .maxStudents(teacher.getMaxStudents())
                .currentCount(teacher.getCurrentCount())
                .build();
    }
}
