package com.example.labmanagement.controller;

import com.example.labmanagement.model.Lab;
import com.example.labmanagement.repository.LabRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/labs")
public class LabControllerAdmin {
    private final LabRepository labRepository;

    public LabControllerAdmin(LabRepository labRepository) { this.labRepository = labRepository; }

    @GetMapping
    public List<Lab> all() { return labRepository.findAll(); }

    @PostMapping
    public Lab create(@RequestBody Lab l) { return labRepository.save(l); }

    @PutMapping("/{id}")
    public Lab update(@PathVariable Integer id, @RequestBody Lab l) { l.setId(id); return labRepository.save(l); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { labRepository.deleteById(id); }
}
