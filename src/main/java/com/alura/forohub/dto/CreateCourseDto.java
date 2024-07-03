package com.alura.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateCourseDto(

        @NotBlank
        @Size(max = 32)
        String name,

        @NotBlank
        @Size(max = 32)
        String category
) {
}
