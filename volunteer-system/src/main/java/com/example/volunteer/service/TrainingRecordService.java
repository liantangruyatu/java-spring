package com.example.volunteer.service;

import com.example.volunteer.model.TrainingRecord;
import com.example.volunteer.repository.TrainingRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingRecordService {

    private final TrainingRecordRepository repository;

    public TrainingRecordService(TrainingRecordRepository repository) {
        this.repository = repository;
    }

    public List<TrainingRecord> findByVolunteerId(Long volunteerId) {
        return repository.findByVolunteerId(volunteerId);
    }

    public Optional<TrainingRecord> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public TrainingRecord save(TrainingRecord record) {
        return repository.save(record);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}


