package com.example.dormmanagement.repository;

import com.example.dormmanagement.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayRepository extends JpaRepository<Stay, Integer> {
    List<Stay> findByStudentId(Integer studentId);
}
