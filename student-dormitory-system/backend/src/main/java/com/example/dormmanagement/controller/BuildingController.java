package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Building;
import com.example.dormmanagement.repository.BuildingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
public class BuildingController {
    private final BuildingRepository repo;
    public BuildingController(BuildingRepository repo){ this.repo = repo; }

    @GetMapping
    public List<Building> list(){ return repo.findAll(); }

    @PostMapping
    public Building create(@RequestBody Building b){ return repo.save(b); }

    @PutMapping("/{id}")
    public Building update(@PathVariable Integer id, @RequestBody Building b){
        b.setId(id);
        return repo.save(b);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){ repo.deleteById(id); }
}
