package com.alura.forohub.dto;

import com.alura.forohub.model.Course;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CourseDto(
        String name,
        String category
) {
    public CourseDto(Course course){
        this(course.getName(), course.getCategory().toString());
    }
}
