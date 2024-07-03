package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity createUser(@RequestBody @Valid CreateUserDto createUserDto){
        return userService.registerUser(createUserDto);
    }

}
