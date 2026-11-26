package com.example.labmanagement.repository;

import com.example.labmanagement.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperimentRepository extends JpaRepository<Experiment, Integer> {
}
