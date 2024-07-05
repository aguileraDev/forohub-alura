package com.alura.forohub.dto;

import com.alura.forohub.model.Answer;

import java.time.Instant;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record AnswerDto(
        Long id,
        String message,
        Boolean solution,
        Instant created_at
) {
    public AnswerDto(Answer answer){
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getSolution(),
                answer.getCreated_at());
    }


}
