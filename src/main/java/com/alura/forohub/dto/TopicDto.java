package com.alura.forohub.dto;

import com.alura.forohub.model.Topic;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record TopicDto(
        Long id,
        String title,
        String message,
        UserDto author,
        CourseDto course,
        List<AnswerDto> answers,
        Boolean status,
        Instant created_at
) {
    public TopicDto(Topic topic){
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                new UserDto(topic.getAuthor()),
                new CourseDto(topic.getCourse()),
                topic.getAnswers() != null ? topic.getAnswers().stream().map(AnswerDto::new).toList() : Collections.emptyList(),
                topic.getStatus(),
                topic.getCreated_at());
    }
}
