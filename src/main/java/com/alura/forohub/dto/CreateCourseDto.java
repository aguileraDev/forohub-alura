package com.alura.forohub.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateCourseDto(

        @NotBlank
        @Size(max = 32)
        @JsonAlias({"name","nombre"})
        String name,

        @NotBlank
        @Size(max = 32)
        @JsonAlias({"category","categoria"})
        String category
) {
}
