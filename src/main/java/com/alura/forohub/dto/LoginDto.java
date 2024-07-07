package com.alura.forohub.dto;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public record LoginDto(
        Long id,
        String username,
        String token,
        String expire_at
) {

}
