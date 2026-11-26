package com.graduation.controller;

import com.graduation.dto.SelectionDTO;
import com.graduation.dto.StudentDTO;
import com.graduation.dto.TeacherDTO;
import com.graduation.dto.TopicDTO;
import com.graduation.entity.Selection;
import com.graduation.entity.Student;
import com.graduation.entity.Teacher;
import com.graduation.entity.Topic;
import com.graduation.entity.User;
import com.graduation.service.SelectionService;
import com.graduation.service.StudentService;
import com.graduation.service.TeacherService;
import com.graduation.service.TopicService;
import com.graduation.service.UserService;
import com.graduation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生选题控制器
 */
@RestController
@RequestMapping("/selection")
public class SelectionController {
    
    @Autowired
    private SelectionService selectionService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 获取所有选题
     */
    @GetMapping("/list")
    public Result<List<SelectionDTO>> getSelectionList() {
        List<Selection> selections = selectionService.list();
        List<SelectionDTO> selectionDTOS = selections.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(selectionDTOS);
    }
    
    /**
     * 根据ID获取选题
     */
    @GetMapping("/get/{id}")
    public Result<SelectionDTO> getSelectionById(@PathVariable Integer id) {
        Selection selection = selectionService.getById(id);
        return Result.success(convertToDTO(selection));
    }
    
    /**
     * 申请选题
     */
    @PostMapping("/apply")
    public Result<Boolean> applySelection(@RequestBody Selection selection) {
        boolean result = selectionService.applySelection(selection);
        if (result) {
            return Result.success(true);
        } else {
            return Result.error("选题申请失败");
        }
    }
    
    /**
     * 更新选题
     */
    @PostMapping("/update")
    public Result<Boolean> updateSelection(@RequestBody Selection selection) {
        return Result.success(selectionService.updateById(selection));
    }
    
    /**
     * 删除选题
     */
    @PostMapping("/delete/{id}")
    public Result<Boolean> deleteSelection(@PathVariable Integer id) {
        return Result.success(selectionService.removeById(id));
    }
    
    /**
     * 根据学生ID获取选题
     */
    @GetMapping("/listByStudent")
    public Result<List<SelectionDTO>> getSelectionListByStudent(@RequestParam Integer studentId) {
        List<Selection> selections = selectionService.findByStudentId(studentId);
        List<SelectionDTO> selectionDTOS = selections.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(selectionDTOS);
    }
    
    /**
     * 根据教师ID获取选题
     */
    @GetMapping("/listByTeacher")
    public Result<List<SelectionDTO>> getSelectionListByTeacher(@RequestParam Integer teacherId) {
        List<Selection> selections = selectionService.findByTeacherId(teacherId);
        List<SelectionDTO> selectionDTOS = selections.stream().map(this::convertToDTO).collect(Collectors.toList());
        return Result.success(selectionDTOS);
    }
    
    /**
     * 审批选题
     */
    @PostMapping("/approve")
    public Result<Boolean> approveSelection(@RequestParam Integer selectionId, 
                                           @RequestParam Integer status, 
                                           @RequestParam(required = false) String comment) {
        return Result.success(selectionService.approveSelection(selectionId, status, comment));
    }
    
    /**
     * 将Selection实体转换为SelectionDTO
     */
    private SelectionDTO convertToDTO(Selection selection) {
        Student student = studentService.getById(selection.getStudentId());
        Topic topic = topicService.getById(selection.getTopicId());
        Teacher teacher = teacherService.getById(selection.getTeacherId());
        
        TopicDTO topicDTO;
        if (topic != null) {
            topicDTO = convertTopicToDTO(topic, teacher);
        } else {
            // 自定义题目情况
            topicDTO = TopicDTO.builder()
                    .id(null)
                    .title(selection.getCustomTitle())
                    .type(null)
                    .description(selection.getCustomDescription())
                    .maxStudents(null)
                    .currentStudents(null)
                    .teacher(convertTeacherToDTO(teacher))
                    .build();
        }
        
        return SelectionDTO.builder()
                .id(selection.getSelectionId())
                .student(convertStudentToDTO(student))
                .topic(topicDTO)
                .status(mapStatus(selection.getStatus()))
                .createTime(selection.getApplyTime())
                .selectionType(selection.getSelectionType())
                .customTitle(selection.getCustomTitle())
                .customDescription(selection.getCustomDescription())
                .approvalComment(selection.getApprovalComment())
                .applyTime(selection.getApplyTime())
                .approvalTime(selection.getApprovalTime())
                .build();
    }
    
    /**
     * 将Student实体转换为StudentDTO
     */
    private StudentDTO convertStudentToDTO(Student student) {
        if (student == null) {
            return null;
        }
        User user = userService.getById(student.getUserId());
        return StudentDTO.builder()
                .id(student.getStudentId())
                .name(student.getRealName())
                .username(user != null ? user.getUsername() : "")
                .gender("男")
                .clazz(student.getClassName())
                .major(student.getMajor())
                .email(user != null ? user.getEmail() : "")
                .phone(student.getPhone())
                .studentNo(student.getStudentNo())
                .build();
    }
    
    /**
     * 将Topic实体转换为TopicDTO
     */
    private TopicDTO convertTopicToDTO(Topic topic, Teacher teacher) {
        return TopicDTO.builder()
                .id(topic.getTopicId())
                .title(topic.getTitle())
                .type(topic.getType().toString())
                .description(topic.getDescription())
                .maxStudents(topic.getMaxStudents())
                .currentStudents(topic.getSelectedCount())
                .teacher(convertTeacherToDTO(teacher))
                .build();
    }
    
    /**
     * 将Teacher实体转换为TeacherDTO
     */
    private TeacherDTO convertTeacherToDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        User user = userService.getById(teacher.getUserId());
        return TeacherDTO.builder()
                .id(teacher.getTeacherId())
                .name(teacher.getRealName())
                .username(user != null ? user.getUsername() : "")
                .gender("男")
                .title("教授")
                .email(user != null ? user.getEmail() : "")
                .phone(teacher.getPhone())
                .department(teacher.getDepartment())
                .researchArea(teacher.getResearchArea())
                .maxStudents(teacher.getMaxStudents())
                .currentCount(teacher.getCurrentCount())
                .build();
    }
    
    /**
     * 状态映射
     */
    private String mapStatus(Integer status) {
        switch (status) {
            case 0: return "待审核";
            case 1: return "已通过";
            case 2: return "已拒绝";
            default: return "未知状态";
        }
    }
}
