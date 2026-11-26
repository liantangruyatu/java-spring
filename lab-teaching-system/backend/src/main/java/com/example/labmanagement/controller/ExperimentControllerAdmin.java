package com.example.labmanagement.controller;

import com.example.labmanagement.model.Experiment;
import com.example.labmanagement.repository.ExperimentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/experiments")
public class ExperimentControllerAdmin {
    private final ExperimentRepository experimentRepository;

    public ExperimentControllerAdmin(ExperimentRepository experimentRepository) {
        this.experimentRepository = experimentRepository;
    }

    @GetMapping
    public List<Experiment> all() { return experimentRepository.findAll(); }

    @PostMapping
    public Experiment create(@RequestBody Experiment e) { return experimentRepository.save(e); }

    @PutMapping("/{id}")
    public Experiment update(@PathVariable Integer id, @RequestBody Experiment e) {
        e.setId(id);
        return experimentRepository.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { experimentRepository.deleteById(id); }
}
