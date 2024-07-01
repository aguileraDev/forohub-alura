package com.alura.forohub.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateTopicDto(
        @NotNull
        @JsonAlias({"title","titulo"})
        @Size(max = 64)
        String title,

        @NotNull
        @JsonAlias({"message","msg"})
        @Size(max = 1024)
        String message

) {
}
