package com.alura.forohub.dto;

import com.alura.forohub.model.User;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record UserDto(
        Long id,
        String name
) {
    public UserDto(User user){
        this(user.getId(), user.getName());
    }
}
