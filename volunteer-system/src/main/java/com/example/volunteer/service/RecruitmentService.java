package com.example.volunteer.service;

import com.example.volunteer.model.Recruitment;
import com.example.volunteer.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    public List<Recruitment> findAll() {
        return recruitmentRepository.findAll();
    }

    public List<Recruitment> findPublished() {
        return recruitmentRepository.findByStatus(1);
    }

    public Optional<Recruitment> findById(Long id) {
        return recruitmentRepository.findById(id);
    }

    @Transactional
    public Recruitment save(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }

    @Transactional
    public void delete(Long id) {
        recruitmentRepository.deleteById(id);
    }
}



