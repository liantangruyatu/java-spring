package com.example.volunteer.repository;

import com.example.volunteer.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    List<ServiceRecord> findByVolunteerId(Long volunteerId);
}



