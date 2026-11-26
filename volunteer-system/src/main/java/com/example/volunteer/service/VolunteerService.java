package com.example.volunteer.service;

import com.example.volunteer.model.User;
import com.example.volunteer.model.Volunteer;
import com.example.volunteer.repository.VolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Transactional
    public Volunteer createVolunteerForUser(User user) {
        Volunteer volunteer = new Volunteer();
        volunteer.setUser(user);
        volunteer.setAuditStatus(0);
        return volunteerRepository.save(volunteer);
    }

    public Optional<Volunteer> findByUserId(Long userId) {
        return volunteerRepository.findByUserId(userId);
    }

    public Optional<Volunteer> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    public List<Volunteer> findByAuditStatus(Integer status) {
        return volunteerRepository.findByAuditStatus(status);
    }

    @Transactional
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }
}



