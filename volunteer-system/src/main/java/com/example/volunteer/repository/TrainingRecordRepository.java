package com.example.volunteer.repository;

import com.example.volunteer.model.TrainingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Long> {

    List<TrainingRecord> findByVolunteerId(Long volunteerId);
}



