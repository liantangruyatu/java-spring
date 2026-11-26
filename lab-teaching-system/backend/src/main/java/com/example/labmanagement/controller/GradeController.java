package com.example.labmanagement.controller;

import com.example.labmanagement.model.Grade;
import com.example.labmanagement.model.Report;
import com.example.labmanagement.model.ReportStatus;
import com.example.labmanagement.repository.GradeRepository;
import com.example.labmanagement.repository.ReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class GradeController {

    private final GradeRepository gradeRepository;
    private final ReportRepository reportRepository;

    public GradeController(GradeRepository gradeRepository, ReportRepository reportRepository) {
        this.gradeRepository = gradeRepository;
        this.reportRepository = reportRepository;
    }

    @PostMapping("/grades")
    public Grade gradeReport(@RequestBody Grade g) {
        Grade saved = gradeRepository.save(g);
        // 将对应报告状态置为 GRADED
        Report rep = reportRepository.findById(g.getReportId()).orElse(null);
        if (rep != null) {
            rep.setStatus(ReportStatus.GRADED);
            reportRepository.save(rep);
        }
        return saved;
    }

    @GetMapping("/grades/by-report/{reportId}")
    public Grade getGradeByReport(@PathVariable Integer reportId) {
        return gradeRepository.findByReportId(reportId);
    }

    @GetMapping("/grades/user/{userId}")
    public List<Grade> getUserGrades(@PathVariable Integer userId) {
        return gradeRepository.findAll().stream()
                .filter(g -> reportRepository.findById(g.getReportId())
                        .map(r -> r.getUserId().equals(userId))
                        .orElse(false))
                .collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/grades")
    public List<Grade> listAll() {
        return gradeRepository.findAll();
    }
}
