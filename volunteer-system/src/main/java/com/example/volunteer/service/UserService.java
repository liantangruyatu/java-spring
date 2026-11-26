package com.example.volunteer.service;

import com.example.volunteer.model.Role;
import com.example.volunteer.model.User;
import com.example.volunteer.repository.RoleRepository;
import com.example.volunteer.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User createUser(String username, String password, String roleName, String fullName, String phone, String email) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("角色不存在"));
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStatus(1);
        return userRepository.save(user);
    }

    @Transactional
    public void resetPassword(Long id, String newPassword) {
        userRepository.findById(id).ifPresent(user -> {
            user.setPassword(newPassword);
            userRepository.save(user);
        });
    }

    @Transactional
    public User createAdminIfNotExists() {
        Optional<User> adminOpt = userRepository.findByUsername("admin");
        if (adminOpt.isPresent()) {
            return adminOpt.get();
        }
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("ADMIN");
                    r.setDescription("系统管理员");
                    return roleRepository.save(r);
                });

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole(adminRole);
        admin.setFullName("系统管理员");
        admin.setStatus(1);
        return userRepository.save(admin);
    }

    @Transactional
    public User registerVolunteerUser(String username, String password, String fullName, String phone, String email) {
        Role volRole = roleRepository.findByName("VOLUNTEER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName("VOLUNTEER");
                    r.setDescription("志愿者");
                    return roleRepository.save(r);
                });
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(volRole);
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStatus(1);
        return userRepository.save(user);
    }
}


