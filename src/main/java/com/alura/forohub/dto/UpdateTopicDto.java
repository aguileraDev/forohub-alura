package com.alura.forohub.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record UpdateTopicDto(

        @JsonAlias({"title","titulo"})
        @Size(max = 64)
        String title,

        @JsonAlias({"message","msg"})
        @Size(max = 1024)
        String message,

        String author,

        Integer course

) {
}
