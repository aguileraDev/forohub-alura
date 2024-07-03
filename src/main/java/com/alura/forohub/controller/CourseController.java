package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.services.CourseService;
import com.alura.forohub.services.ForoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;

    @Autowired
    private CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity createCourse(@RequestBody @Valid CreateCourseDto createCourseDto){
        return courseService.registerCourse(createCourseDto);
    }
}
