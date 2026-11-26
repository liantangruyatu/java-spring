package com.example.labmanagement.controller;

import com.example.labmanagement.model.Report;
import com.example.labmanagement.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportRepository reportRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @PostMapping("/student/reports")
    public Report uploadReport(@RequestParam("userId") Integer userId,
                               @RequestParam("experimentId") Integer experimentId,
                               @RequestParam("file") MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Path target = uploadPath.resolve(System.currentTimeMillis() + "_" + filename);
        Files.copy(file.getInputStream(), target);

        Report r = new Report();
        r.setUserId(userId);
        r.setExperimentId(experimentId);
        r.setFilePath(target.toString());
        r.setSubmittedAt(LocalDateTime.now());
        r.setStatus(com.example.labmanagement.model.ReportStatus.SUBMITTED);
        return reportRepository.save(r);
    }

    @GetMapping("/student/reports/my")
    public List<Report> getMyReports(@RequestParam Integer userId) {
        return reportRepository.findByUserId(userId);
    }

    @GetMapping("/student/reports/{id}")
    public Report getReport(@PathVariable Integer id) {
        return reportRepository.findById(id).orElse(null);
    }

    @GetMapping("/teacher/reports/pending")
    public List<Report> getPendingReports() {
        return reportRepository.findByStatus(com.example.labmanagement.model.ReportStatus.SUBMITTED);
    }
}
