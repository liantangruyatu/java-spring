package com.example.labmanagement.controller;

import com.example.labmanagement.model.Course;
import com.example.labmanagement.model.Experiment;
import com.example.labmanagement.repository.CourseRepository;
import com.example.labmanagement.repository.ExperimentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CourseController {

    private final CourseRepository courseRepository;
    private final ExperimentRepository experimentRepository;

    public CourseController(CourseRepository courseRepository, ExperimentRepository experimentRepository) {
        this.courseRepository = courseRepository;
        this.experimentRepository = experimentRepository;
    }

    @GetMapping("/courses")
    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}/experiments")
    public List<Experiment> listByCourse(@PathVariable Long id) {
        return experimentRepository.findAll().stream().filter(e -> e.getCourseId() != null && e.getCourseId().equals(id)).collect(java.util.stream.Collectors.toList());
    }
}
