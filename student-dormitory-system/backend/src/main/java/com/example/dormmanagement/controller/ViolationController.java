package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Violation;
import com.example.dormmanagement.repository.ViolationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/violations")
public class ViolationController {
    private final ViolationRepository repo;
    public ViolationController(ViolationRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Violation> list(@RequestParam(required = false) Boolean processed){
        if(processed != null) return repo.findAll().stream().filter(v -> v.getProcessed().equals(processed)).toList();
        return repo.findAll();
    }

    @PostMapping
    public Violation create(@RequestBody Violation v){ return repo.save(v); }

    @PutMapping("/{id}")
    public Violation update(@PathVariable Integer id, @RequestBody Violation v){ v.setId(id); return repo.save(v); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
