package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Student;
import com.example.dormmanagement.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repo;
    public StudentController(StudentRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Student> list(){ return repo.findAll(); }

    @PostMapping
    public Student create(@RequestBody Student s){ return repo.save(s); }

    @PutMapping("/{id}")
    public Student update(@PathVariable Integer id, @RequestBody Student s){ s.setId(id); return repo.save(s); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
