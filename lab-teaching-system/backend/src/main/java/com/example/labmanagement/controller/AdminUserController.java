package com.example.labmanagement.controller;

import com.example.labmanagement.model.User;
import com.example.labmanagement.model.RoleEnum;
import com.example.labmanagement.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsersByRole(@RequestParam(required = false) String role) {
        if (role == null || role.isEmpty()) {
            return userRepository.findAll();
        }
        try {
            RoleEnum roleEnum = RoleEnum.valueOf(role.toUpperCase());
            return userRepository.findByRole(roleEnum);
        } catch (IllegalArgumentException e) {
            return userRepository.findAll();
        }
    }

    @GetMapping("/users/teachers")
    public List<User> getTeachers() {
        return userRepository.findByRole(RoleEnum.TEACHER);
    }

    @GetMapping("/users/students")
    public List<User> getStudents() {
        return userRepository.findByRole(RoleEnum.STUDENT);
    }
}