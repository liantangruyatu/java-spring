package com.example.volunteer.service;

import com.example.volunteer.model.ServiceRecord;
import com.example.volunteer.repository.ServiceRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRecordService {

    private final ServiceRecordRepository repository;

    public ServiceRecordService(ServiceRecordRepository repository) {
        this.repository = repository;
    }

    public List<ServiceRecord> findByVolunteerId(Long volunteerId) {
        return repository.findByVolunteerId(volunteerId);
    }

    public Optional<ServiceRecord> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ServiceRecord save(ServiceRecord record) {
        return repository.save(record);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}

