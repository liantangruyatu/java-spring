package com.example.labmanagement.controller;

import com.example.labmanagement.model.Course;
import com.example.labmanagement.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
public class AdminCourseController {

    private final CourseRepository courseRepository;

    public AdminCourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> all() { return courseRepository.findAll(); }

    @PostMapping
    public Course create(@RequestBody Course c) { return courseRepository.save(c); }

    @PutMapping("/{id}")
    public Course update(@PathVariable Integer id, @RequestBody Course c) {
        c.setId(id);
        return courseRepository.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { courseRepository.deleteById(id); }
}
