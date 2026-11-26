package com.example.volunteer.repository;

import com.example.volunteer.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    List<Recruitment> findByStatus(Integer status);
}



