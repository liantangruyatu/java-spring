package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Dorm;
import com.example.dormmanagement.repository.DormRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dorms")
public class DormController {
    private final DormRepository repo;
    public DormController(DormRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Dorm> list(){ return repo.findAll(); }

    @PostMapping
    public Dorm create(@RequestBody Dorm d){ return repo.save(d); }

    @PutMapping("/{id}")
    public Dorm update(@PathVariable Integer id, @RequestBody Dorm d){ d.setId(id); return repo.save(d); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
