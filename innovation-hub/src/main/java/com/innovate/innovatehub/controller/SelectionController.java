package com.innovate.innovatehub.controller;

import com.innovate.innovatehub.entity.Selection;
import com.innovate.innovatehub.service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选题记录控制器
 */
@RestController
@RequestMapping("/api/selections")
@CrossOrigin
public class SelectionController {
    
    @Autowired
    private SelectionService selectionService;
    
    @GetMapping
    public Map<String, Object> getAllSelections() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Selection> selections = selectionService.getAllSelections();
            result.put("success", true);
            result.put("data", selections);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getSelectionById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Selection selection = selectionService.getSelectionById(id);
            result.put("success", true);
            result.put("data", selection);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/student/{studentId}")
    public Map<String, Object> getSelectionsByStudent(@PathVariable Integer studentId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Selection> selections = selectionService.getSelectionsByStudentId(studentId);
            result.put("success", true);
            result.put("data", selections);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addSelection(@RequestBody Selection selection) {
        Map<String, Object> result = new HashMap<>();
        try {
            selectionService.addSelection(selection);
            result.put("success", true);
            result.put("message", "选题成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updateSelection(@PathVariable Integer id, @RequestBody Selection selection) {
        Map<String, Object> result = new HashMap<>();
        try {
            selection.setSelectionId(id);
            selectionService.updateSelection(selection);
            result.put("success", true);
            result.put("message", "选题更新成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteSelection(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            selectionService.deleteSelection(id);
            result.put("success", true);
            result.put("message", "选题删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
