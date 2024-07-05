package com.alura.forohub.controller;

import com.alura.forohub.dto.CourseDto;
import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.services.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
        CourseDto course = courseService.registerCourse(createCourseDto);
        URI uri = UriComponentsBuilder.fromUriString("/courses/{id}").buildAndExpand(course.id()).toUri();
        return ResponseEntity.created(uri).body(course);
    }
}
