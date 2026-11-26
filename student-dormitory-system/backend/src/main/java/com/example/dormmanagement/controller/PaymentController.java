package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Payment;
import com.example.dormmanagement.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentRepository repo;
    public PaymentController(PaymentRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Payment> list(@RequestParam(required = false) Boolean paid){
        if(paid != null) return repo.findAll().stream().filter(p -> p.getPaid().equals(paid)).toList();
        return repo.findAll();
    }

    @PostMapping
    public Payment create(@RequestBody Payment p){ return repo.save(p); }

    @PutMapping("/{id}")
    public Payment update(@PathVariable Integer id, @RequestBody Payment p){ p.setId(id); return repo.save(p); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
