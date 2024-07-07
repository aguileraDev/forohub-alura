package com.alura.forohub.dto;

import com.alura.forohub.model.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record NewLoginDto(
        @JsonIgnore
        Long id,
        @NotBlank
        @JsonAlias({"username","usuario"})
        String username,
        @NotBlank
        @JsonAlias({"password","clave"})
        String password
) {
    public NewLoginDto(User user){
        this(user.getId(), user.getUsername(), user.getPassword());
    }
}
