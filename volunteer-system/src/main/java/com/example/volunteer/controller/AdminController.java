package com.example.volunteer.controller;

import com.example.volunteer.model.Recruitment;
import com.example.volunteer.model.RecruitmentApplication;
import com.example.volunteer.model.Role;
import com.example.volunteer.model.User;
import com.example.volunteer.model.Volunteer;
import com.example.volunteer.repository.RoleRepository;
import com.example.volunteer.service.RecruitmentApplicationService;
import com.example.volunteer.service.RecruitmentService;
import com.example.volunteer.service.UserService;
import com.example.volunteer.service.VolunteerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final VolunteerService volunteerService;
    private final RecruitmentService recruitmentService;
    private final RecruitmentApplicationService applicationService;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(VolunteerService volunteerService,
                           RecruitmentService recruitmentService,
                           RecruitmentApplicationService applicationService,
                           UserService userService,
                           RoleRepository roleRepository) {
        this.volunteerService = volunteerService;
        this.recruitmentService = recruitmentService;
        this.applicationService = applicationService;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    private boolean ensureAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() != null && "ADMIN".equalsIgnoreCase(user.getRole().getName());
    }

    @GetMapping("/admin/volunteers")
    public String volunteers(HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        List<Volunteer> list = volunteerService.findAll();
        model.addAttribute("list", list);
        return "admin/volunteers";
    }

    @PostMapping("/admin/volunteers/{id}/audit")
    public String auditVolunteer(@PathVariable("id") Long id,
                                 @RequestParam Integer status,
                                 @RequestParam(required = false) String remark,
                                 HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        volunteerService.findById(id).ifPresent(v -> {
            v.setAuditStatus(status);
            v.setAuditRemark(remark);
            volunteerService.save(v);
        });
        return "redirect:/admin/volunteers";
    }

    @GetMapping("/admin/recruitments")
    public String recruitments(HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        model.addAttribute("list", recruitmentService.findAll());
        return "admin/recruitments";
    }

    @GetMapping("/admin/recruitments/new")
    public String newRecruitment(HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        model.addAttribute("recruitment", new Recruitment());
        return "admin/recruitment-form";
    }

    @GetMapping("/admin/recruitments/{id}/edit")
    public String editRecruitment(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        Optional<Recruitment> rec = recruitmentService.findById(id);
        rec.ifPresent(r -> model.addAttribute("recruitment", r));
        return "admin/recruitment-form";
    }

    @PostMapping("/admin/recruitments/save")
    public String saveRecruitment(@RequestParam(required = false) Long id,
                                  @RequestParam String title,
                                  @RequestParam String description,
                                  @RequestParam String location,
                                  @RequestParam String startTime,
                                  @RequestParam String endTime,
                                  @RequestParam(required = false) String requirement,
                                  @RequestParam Integer maxVolunteers,
                                  @RequestParam Integer status,
                                  HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        User admin = (User) session.getAttribute("user");
        Recruitment r = (id != null) ? recruitmentService.findById(id).orElse(new Recruitment()) : new Recruitment();
        r.setTitle(title);
        r.setDescription(description);
        r.setLocation(location);
        r.setStartTime(LocalDateTime.parse(startTime));
        r.setEndTime(LocalDateTime.parse(endTime));
        r.setRequirement(requirement);
        r.setMaxVolunteers(maxVolunteers);
        r.setStatus(status);
        r.setCreatedBy(admin);
        recruitmentService.save(r);
        return "redirect:/admin/recruitments";
    }

    @PostMapping("/admin/recruitments/{id}/delete")
    public String deleteRecruitment(@PathVariable("id") Long id, HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        recruitmentService.delete(id);
        return "redirect:/admin/recruitments";
    }

    @GetMapping("/admin/recruitments/{id}/applications")
    public String viewApplications(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        List<RecruitmentApplication> apps = applicationService.findByRecruitmentId(id);
        model.addAttribute("apps", apps);
        model.addAttribute("recruitmentId", id);
        return "admin/applications";
    }

    @PostMapping("/admin/applications/{id}/status")
    public String updateApplicationStatus(@PathVariable("id") Long id,
                                          @RequestParam Integer status,
                                          HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        applicationService.findById(id).ifPresent(app -> {
            app.setStatus(status);
            applicationService.save(app);
        });
        return "redirect:/admin/recruitments";
    }

    @GetMapping("/admin/users")
    public String users(HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        model.addAttribute("list", userService.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users";
    }

    @PostMapping("/admin/users/save")
    public String saveUser(@RequestParam(required = false) Long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String roleName,
                           @RequestParam(required = false) String fullName,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) String email,
                           HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        if (id != null) {
            userService.findById(id).ifPresent(user -> {
                user.setUsername(username);
                if (password != null && !password.isEmpty()) {
                    user.setPassword(password);
                }
                Role role = roleRepository.findByName(roleName).orElse(null);
                if (role != null) user.setRole(role);
                user.setFullName(fullName);
                user.setPhone(phone);
                user.setEmail(email);
                userService.save(user);
            });
        } else {
            userService.createUser(username, password, roleName, fullName, phone, email);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        userService.findById(id).ifPresent(user -> model.addAttribute("user", user));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/user-form";
    }

    @GetMapping("/admin/users/new")
    public String newUser(HttpSession session, Model model) {
        if (!ensureAdmin(session)) return "redirect:/login";
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/user-form";
    }

    @PostMapping("/admin/users/{id}/reset-password")
    public String resetPassword(@PathVariable("id") Long id,
                                @RequestParam String newPassword,
                                HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        userService.resetPassword(id, newPassword);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id, HttpSession session) {
        if (!ensureAdmin(session)) return "redirect:/login";
        userService.delete(id);
        return "redirect:/admin/users";
    }
}


