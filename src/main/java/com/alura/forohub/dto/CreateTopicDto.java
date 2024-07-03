package com.alura.forohub.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateTopicDto(
        @NotBlank
        @JsonAlias({"title","titulo"})
        @Size(max = 64)
        String title,

        @NotBlank
        @JsonAlias({"message","msg"})
        @Size(max = 1024)
        String message,

        @NotBlank
        String author,

        @NotNull
        Integer course


) {
}
