package com.alura.forohub.dto;

import com.alura.forohub.model.User;

import java.time.Instant;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record UserDto(
        Long id,
        String name,
        Instant login,
        Boolean isActive
) {
    public UserDto(User user){
        this(user.getId(), user.getName(),user.getLastLogin(), user.getIsActive());
    }
}
