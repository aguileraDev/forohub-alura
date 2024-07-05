package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.dto.UserDto;
import com.alura.forohub.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService service){
        this.userService = service;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto user = userService.registerUser(createUserDto);
        final URI uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findOne(@PathVariable @Valid Integer id){
        UserDto user = userService.getOneUser(String.valueOf(id));
        return ResponseEntity.ok().body(user);
    }

}
