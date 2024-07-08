package com.alura.forohub.controller;

import com.alura.forohub.dto.CourseDto;
import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;

    @Autowired
    private CourseController(CourseService courseService){
        this.courseService = courseService;
    }
    @Operation(summary = "Register a course")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "course created",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = CourseDto.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid json data. fields missing.",
                    content = {@Content})

    })
    @PostMapping
    public ResponseEntity createCourse(@RequestBody @Valid CreateCourseDto createCourseDto){
        CourseDto course = courseService.registerCourse(createCourseDto);
        URI uri = UriComponentsBuilder.fromUriString("/courses/{id}").buildAndExpand(course.id()).toUri();
        return ResponseEntity.created(uri).body(course);
    }
}
