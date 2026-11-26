package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Stay;
import com.example.dormmanagement.repository.StayRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stays")
public class StayController {
    private final StayRepository repo;
    public StayController(StayRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Stay> list(){ return repo.findAll(); }

    @PostMapping
    public Stay create(@RequestBody Stay s){ return repo.save(s); }

    @PutMapping("/{id}")
    public Stay update(@PathVariable Integer id, @RequestBody Stay s){ s.setId(id); return repo.save(s); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
