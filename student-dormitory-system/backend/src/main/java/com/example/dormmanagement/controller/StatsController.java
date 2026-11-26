package com.example.dormmanagement.controller;

import com.example.dormmanagement.entity.Dorm;
import com.example.dormmanagement.entity.Stay;
import com.example.dormmanagement.repository.DormRepository;
import com.example.dormmanagement.repository.StayRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final DormRepository dormRepo;
    private final StayRepository stayRepo;

    public StatsController(DormRepository dormRepo, StayRepository stayRepo) {
        this.dormRepo = dormRepo;
        this.stayRepo = stayRepo;
    }

    @GetMapping("/vacant-beds")
    public List<Map<String, Object>> getVacantBeds() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Dorm> dorms = dormRepo.findAll();
        for (Dorm dorm : dorms) {
            int capacity = dorm.getCapacity() != null ? dorm.getCapacity() : 0;
            long occupied = stayRepo.findAll().stream()
                    .filter(s -> s.getDorm().getId().equals(dorm.getId()) && s.getActive())
                    .count();
            int vacant = (int) (capacity - occupied);
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("dormId", dorm.getId());
            map.put("roomNo", dorm.getRoomNo());
            map.put("building", dorm.getBuilding().getName());
            map.put("capacity", capacity);
            map.put("occupied", occupied);
            map.put("vacant", Math.max(vacant, 0));
            result.add(map);
        }
        return result;
    }

    @GetMapping("/class-distribution")
    public Map<String, Integer> getClassDistribution() {
        List<Stay> stays = stayRepo.findAll().stream()
                .filter(s -> s.getActive())
                .toList();
        return stays.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStudent().getStudentClass(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }
}
