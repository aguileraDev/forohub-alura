package com.alura.forohub.dto;

import com.alura.forohub.model.Topic;

import java.time.Instant;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record TopicDto(
        Long id,
        String title,
        String message,
        UserDto author,
        CourseDto course,
        Instant created_at
) {
    public TopicDto(Topic topic){
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                new UserDto(topic.getAuthor()),
                new CourseDto(topic.getCourse()),
                topic.getCreated_at());
    }
}
