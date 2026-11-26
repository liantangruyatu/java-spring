package com.graduation.controller;

import com.graduation.entity.Progress;
import com.graduation.service.ProgressService;
import com.graduation.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 毕设进度控制器
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {
    
    @Autowired
    private ProgressService progressService;
    
    /**
     * 获取所有进度
     */
    @GetMapping("/list")
    public Result<List<Progress>> getProgressList() {
        return Result.success(progressService.list());
    }
    
    /**
     * 根据ID获取进度
     */
    @GetMapping("/get/{id}")
    public Result<Progress> getProgressById(@PathVariable Integer id) {
        return Result.success(progressService.getById(id));
    }
    
    /**
     * 新增进度
     */
    @PostMapping("/add")
    public Result<Boolean> addProgress(@RequestBody Progress progress) {
        return Result.success(progressService.save(progress));
    }
    
    /**
     * 更新进度
     */
    @PostMapping("/update")
    public Result<Boolean> updateProgress(@RequestBody Progress progress) {
        return Result.success(progressService.updateById(progress));
    }
    
    /**
     * 删除进度
     */
    @PostMapping("/delete/{id}")
    public Result<Boolean> deleteProgress(@PathVariable Integer id) {
        return Result.success(progressService.removeById(id));
    }
    
    /**
     * 根据学生ID获取进度
     */
    @GetMapping("/listByStudent")
    public Result<List<Progress>> getProgressListByStudent(@RequestParam Integer studentId) {
        return Result.success(progressService.findByStudentId(studentId));
    }
    
    /**
     * 根据教师ID获取进度
     */
    @GetMapping("/listByTeacher")
    public Result<List<Progress>> getProgressListByTeacher(@RequestParam Integer teacherId) {
        return Result.success(progressService.findByTeacherId(teacherId));
    }
    
    /**
     * 根据选题ID获取进度
     */
    @GetMapping("/listBySelection")
    public Result<List<Progress>> getProgressListBySelection(@RequestParam Integer selectionId) {
        return Result.success(progressService.findBySelectionId(selectionId));
    }
    
    /**
     * 评价进度
     */
    @PostMapping("/evaluate")
    public Result<Boolean> evaluateProgress(@RequestParam Integer progressId, 
                                           @RequestParam Integer status, 
                                           @RequestParam(required = false) String comment) {
        return Result.success(progressService.updateProgressStatus(progressId, status, comment));
    }
}
