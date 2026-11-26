package com.example.volunteer.controller;

import com.example.volunteer.model.Recruitment;
import com.example.volunteer.model.RecruitmentApplication;
import com.example.volunteer.model.ServiceRecord;
import com.example.volunteer.model.TrainingRecord;
import com.example.volunteer.model.User;
import com.example.volunteer.model.Volunteer;
import com.example.volunteer.service.RecruitmentApplicationService;
import com.example.volunteer.service.RecruitmentService;
import com.example.volunteer.service.ServiceRecordService;
import com.example.volunteer.service.TrainingRecordService;
import com.example.volunteer.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final RecruitmentService recruitmentService;
    private final RecruitmentApplicationService applicationService;
    private final ServiceRecordService serviceRecordService;
    private final TrainingRecordService trainingRecordService;

    public VolunteerController(VolunteerService volunteerService,
                               RecruitmentService recruitmentService,
                               RecruitmentApplicationService applicationService,
                               ServiceRecordService serviceRecordService,
                               TrainingRecordService trainingRecordService) {
        this.volunteerService = volunteerService;
        this.recruitmentService = recruitmentService;
        this.applicationService = applicationService;
        this.serviceRecordService = serviceRecordService;
        this.trainingRecordService = trainingRecordService;
    }

    @GetMapping("/volunteer/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Volunteer> volOpt = volunteerService.findByUserId(user.getId());
        volOpt.ifPresent(vol -> model.addAttribute("volunteer", vol));
        return "volunteer/profile";
    }

    @PostMapping("/volunteer/profile")
    public String updateProfile(HttpSession session,
                                @RequestParam(required = false) String gender,
                                @RequestParam(required = false) String birthday,
                                @RequestParam(required = false) String idCard,
                                @RequestParam(required = false) String address,
                                Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Volunteer> volOpt = volunteerService.findByUserId(user.getId());
        if (volOpt.isPresent()) {
            Volunteer v = volOpt.get();
            v.setGender(gender);
            v.setIdCard(idCard);
            v.setAddress(address);
            volunteerService.save(v);
            model.addAttribute("msg", "保存成功");
            model.addAttribute("volunteer", v);
        }
        return "volunteer/profile";
    }

    @GetMapping("/volunteer/recruitments")
    public String listRecruitments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<Recruitment> published = recruitmentService.findPublished();
        model.addAttribute("list", published);
        Volunteer volunteer = volunteerService.findByUserId(user.getId()).orElse(null);
        if (volunteer != null) {
            List<RecruitmentApplication> myApps = applicationService.findByVolunteerId(volunteer.getId());
            model.addAttribute("myApps", myApps);
        }
        return "volunteer/recruitments";
    }

    @PostMapping("/volunteer/recruitments/{id}/apply")
    public String applyRecruitment(@PathVariable("id") Long id,
                                   HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Volunteer> volOpt = volunteerService.findByUserId(user.getId());
        Optional<Recruitment> recOpt = recruitmentService.findById(id);
        if (volOpt.isPresent() && recOpt.isPresent()) {
            applicationService.apply(volOpt.get(), recOpt.get());
            model.addAttribute("msg", "报名成功，待审核");
        }
        return "redirect:/volunteer/recruitments";
    }

    @GetMapping("/volunteer/service-records")
    public String serviceRecords(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Volunteer> volOpt = volunteerService.findByUserId(user.getId());
        if (volOpt.isPresent()) {
            model.addAttribute("records", serviceRecordService.findByVolunteerId(volOpt.get().getId()));
            model.addAttribute("volunteerId", volOpt.get().getId());
        }
        return "volunteer/service-records";
    }

    @PostMapping("/volunteer/service-records/save")
    public String saveServiceRecord(HttpSession session,
                                    @RequestParam Long volunteerId,
                                    @RequestParam String serviceDate,
                                    @RequestParam Double hours,
                                    @RequestParam String content,
                                    @RequestParam(required = false) String evaluation) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        volunteerService.findById(volunteerId).ifPresent(vol -> {
            ServiceRecord record = new ServiceRecord();
            record.setVolunteer(vol);
            record.setServiceDate(java.time.LocalDate.parse(serviceDate));
            record.setHours(hours);
            record.setContent(content);
            record.setEvaluation(evaluation);
            serviceRecordService.save(record);
        });
        return "redirect:/volunteer/service-records";
    }

    @GetMapping("/volunteer/training-records")
    public String trainingRecords(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Volunteer> volOpt = volunteerService.findByUserId(user.getId());
        if (volOpt.isPresent()) {
            model.addAttribute("records", trainingRecordService.findByVolunteerId(volOpt.get().getId()));
            model.addAttribute("volunteerId", volOpt.get().getId());
        }
        return "volunteer/training-records";
    }

    @PostMapping("/volunteer/training-records/save")
    public String saveTrainingRecord(HttpSession session,
                                     @RequestParam Long volunteerId,
                                     @RequestParam String topic,
                                     @RequestParam String trainingDate,
                                     @RequestParam String result) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        volunteerService.findById(volunteerId).ifPresent(vol -> {
            TrainingRecord record = new TrainingRecord();
            record.setVolunteer(vol);
            record.setTopic(topic);
            record.setTrainingDate(java.time.LocalDate.parse(trainingDate));
            record.setResult(result);
            trainingRecordService.save(record);
        });
        return "redirect:/volunteer/training-records";
    }
}


