package com.example.dormmanagement.repository;

import com.example.dormmanagement.entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Integer> {
}
