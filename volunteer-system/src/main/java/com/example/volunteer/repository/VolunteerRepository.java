package com.example.volunteer.repository;

import com.example.volunteer.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByUserId(Long userId);

    List<Volunteer> findByAuditStatus(Integer auditStatus);
}



