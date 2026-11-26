package com.example.volunteer.repository;

import com.example.volunteer.model.RecruitmentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentApplicationRepository extends JpaRepository<RecruitmentApplication, Long> {

    List<RecruitmentApplication> findByVolunteerId(Long volunteerId);

    List<RecruitmentApplication> findByRecruitmentId(Long recruitmentId);
}



