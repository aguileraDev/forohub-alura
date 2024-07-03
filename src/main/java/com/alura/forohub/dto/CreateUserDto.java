package com.alura.forohub.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record CreateUserDto(
        @NotBlank
        @JsonAlias({"full_name"})
        @Size(max = 64)
        String name,

        @NotBlank
        @JsonAlias({"mail"})
        @Size(max = 32)
        @Email
        String email,

        @NotBlank
        @Size(max = 255)
        String password
) {}
