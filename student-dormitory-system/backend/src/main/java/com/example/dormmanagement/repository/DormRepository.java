package com.example.dormmanagement.repository;

import com.example.dormmanagement.entity.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormRepository extends JpaRepository<Dorm, Integer> {
}
