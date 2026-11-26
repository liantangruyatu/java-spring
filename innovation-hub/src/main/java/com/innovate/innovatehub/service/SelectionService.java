package com.innovate.innovatehub.service;

import com.innovate.innovatehub.entity.Selection;
import com.innovate.innovatehub.mapper.SelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 选题记录服务实现类
 */
@Service
public class SelectionService {
    
    @Autowired
    private SelectionMapper selectionMapper;
    
    public List<Selection> getAllSelections() {
        return selectionMapper.findAll();
    }
    
    public Selection getSelectionById(Integer selectionId) {
        return selectionMapper.findById(selectionId);
    }
    
    public List<Selection> getSelectionsByStudentId(Integer studentId) {
        return selectionMapper.findByStudentId(studentId);
    }
    
    public int addSelection(Selection selection) {
        if (selection.getStatus() == null || selection.getStatus().isEmpty()) {
            selection.setStatus("待审核");
        }
        return selectionMapper.insert(selection);
    }
    
    public int updateSelection(Selection selection) {
        return selectionMapper.update(selection);
    }
    
    public int deleteSelection(Integer selectionId) {
        return selectionMapper.delete(selectionId);
    }
}
