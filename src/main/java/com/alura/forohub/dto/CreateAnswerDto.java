package com.alura.forohub.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateAnswerDto(
        @NotBlank
        @Size(max = 1000)
        @JsonAlias({"message","mensaje"})
        String message,
        @NotNull
        @JsonAlias({"topic","topico"})
        Integer topic,
        @NotBlank
        @JsonAlias({"author","autor"})
        String author
) {

}
