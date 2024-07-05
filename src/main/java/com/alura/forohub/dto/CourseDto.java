package com.alura.forohub.dto;

import com.alura.forohub.model.Course;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CourseDto(
        Long id,
        String name,
        String category
) {
    public CourseDto(Course course){
        this(course.getId(), course.getName(), course.getCategory().getCategory());
    }
}
