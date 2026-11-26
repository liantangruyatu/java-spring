package com.example.volunteer.service;

import com.example.volunteer.model.Recruitment;
import com.example.volunteer.model.RecruitmentApplication;
import com.example.volunteer.model.Volunteer;
import com.example.volunteer.repository.RecruitmentApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentApplicationService {

    private final RecruitmentApplicationRepository applicationRepository;

    public RecruitmentApplicationService(RecruitmentApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<RecruitmentApplication> findByVolunteerId(Long volunteerId) {
        return applicationRepository.findByVolunteerId(volunteerId);
    }

    public List<RecruitmentApplication> findByRecruitmentId(Long recruitmentId) {
        return applicationRepository.findByRecruitmentId(recruitmentId);
    }

    public Optional<RecruitmentApplication> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public RecruitmentApplication apply(Volunteer volunteer, Recruitment recruitment) {
        RecruitmentApplication app = new RecruitmentApplication();
        app.setVolunteer(volunteer);
        app.setRecruitment(recruitment);
        app.setStatus(0);
        return applicationRepository.save(app);
    }

    @Transactional
    public RecruitmentApplication save(RecruitmentApplication application) {
        return applicationRepository.save(application);
    }
}



