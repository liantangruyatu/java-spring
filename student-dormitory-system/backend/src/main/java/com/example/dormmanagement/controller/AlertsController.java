package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Payment;
import com.example.dormmanagement.entity.Violation;
import com.example.dormmanagement.repository.PaymentRepository;
import com.example.dormmanagement.repository.ViolationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerts")
public class AlertsController {
    private final PaymentRepository paymentRepo;
    private final ViolationRepository violationRepo;

    public AlertsController(PaymentRepository paymentRepo, ViolationRepository violationRepo) {
        this.paymentRepo = paymentRepo;
        this.violationRepo = violationRepo;
    }

    @GetMapping("/unpaid-payments")
    public List<Map<String, Object>> getUnpaidPayments() {
        return paymentRepo.findAll().stream()
                .filter(p -> !p.getPaid())
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", p.getId());
                    map.put("studentNo", p.getStudent().getStudentNo());
                    map.put("studentName", p.getStudent().getName());
                    map.put("amount", p.getAmount());
                    map.put("dueDate", p.getDueDate());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/unprocessed-violations")
    public List<Map<String, Object>> getUnprocessedViolations() {
        return violationRepo.findAll().stream()
                .filter(v -> !v.getProcessed())
                .map(v -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", v.getId());
                    map.put("studentNo", v.getStudent().getStudentNo());
                    map.put("studentName", v.getStudent().getName());
                    map.put("description", v.getDescription());
                    map.put("occDate", v.getOccDate());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
