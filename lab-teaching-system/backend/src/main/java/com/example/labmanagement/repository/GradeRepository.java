package com.example.labmanagement.repository;

import com.example.labmanagement.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Grade findByReportId(Integer reportId);
    List<Grade> findByGraderId(Integer graderId);
}
