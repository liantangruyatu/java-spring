package com.example.labmanagement.config;

import com.example.labmanagement.model.*;
import com.example.labmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           CourseRepository courseRepository,
                           ExperimentRepository experimentRepository,
                           LabRepository labRepository,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullname("系统管理员");
                admin.setRole(RoleEnum.ADMIN);
                admin.setEmail("admin@example.com");
                userRepository.save(admin);

                User teacher = new User();
                teacher.setUsername("teacher1");
                teacher.setPassword(passwordEncoder.encode("teacher123"));
                teacher.setFullname("张老师");
                teacher.setRole(RoleEnum.TEACHER);
                teacher.setEmail("teacher1@example.com");
                userRepository.save(teacher);

                User student = new User();
                student.setUsername("student1");
                student.setPassword(passwordEncoder.encode("student123"));
                student.setFullname("李学生");
                student.setRole(RoleEnum.STUDENT);
                student.setEmail("student1@example.com");
                userRepository.save(student);
            }

            if (labRepository.count() == 0) {
                Lab l1 = new Lab();
                l1.setName("电工实验室");
                l1.setLocation("教学楼A-101");
                l1.setCapacity(30);
                l1.setStatus(LabStatus.AVAILABLE);
                labRepository.save(l1);

                Lab l2 = new Lab();
                l2.setName("计算机实验室");
                l2.setLocation("教学楼B-202");
                l2.setCapacity(40);
                l2.setStatus(LabStatus.AVAILABLE);
                labRepository.save(l2);
            }

            if (courseRepository.count() == 0) {
                Course c = new Course();
                c.setCode("CS101");
                c.setName("计算机基础");
                c.setTerm("2025春");
                // set teacher id if teacher exists
                userRepository.findByUsername("teacher1").ifPresent(u -> c.setTeacherId(u.getId()));
                courseRepository.save(c);

                Experiment e1 = new Experiment();
                e1.setCourseId(c.getId());
                e1.setTitle("计算机组装实验");
                e1.setDescription("组装并测试计算机硬件");
                e1.setDurationMinutes(120);
                experimentRepository.save(e1);

                Experiment e2 = new Experiment();
                e2.setCourseId(c.getId());
                e2.setTitle("操作系统安装");
                e2.setDescription("安装常见操作系统并配置");
                e2.setDurationMinutes(90);
                experimentRepository.save(e2);
            }
        };
    }
}
