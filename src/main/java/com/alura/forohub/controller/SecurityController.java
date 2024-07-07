package com.alura.forohub.controller;

import com.alura.forohub.dto.LoginDto;
import com.alura.forohub.dto.NewLoginDto;
import com.alura.forohub.model.User;
import com.alura.forohub.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/login")
public class SecurityController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SecurityController(JwtService jwtService, AuthenticationManager authenticationManager){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity registerLogin(@RequestBody @Valid NewLoginDto newLoginDto){
        String username = newLoginDto.username();
        String password = newLoginDto.password();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        newLoginDto = new NewLoginDto((User) authenticationManager.authenticate(authenticationToken).getPrincipal());

        LoginDto loginDto = jwtService.generateJwtToken(newLoginDto);

        return ResponseEntity.ok(loginDto);
    }
}
