package com.example.labmanagement.repository;

import com.example.labmanagement.model.Report;
import com.example.labmanagement.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByUserId(Integer userId);
    List<Report> findByStatus(ReportStatus status);
    List<Report> findByExperimentId(Integer experimentId);
}
