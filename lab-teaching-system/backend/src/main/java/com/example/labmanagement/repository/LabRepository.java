package com.example.labmanagement.repository;

import com.example.labmanagement.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepository extends JpaRepository<Lab, Integer> {
}
