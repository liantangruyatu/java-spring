package com.example.dormmanagement.repository;

import com.example.dormmanagement.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
