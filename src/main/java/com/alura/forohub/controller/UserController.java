package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.dto.UserDto;
import com.alura.forohub.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService service){
        this.userService = service;
    }
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "user created",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid json data. fields missing. mail or full_name exists",
                    content = {@Content})

    })
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto user = userService.registerUser(createUserDto);
        final URI uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);

    }
    @Operation(summary = "Get one user by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user obtained",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = {@Content}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findOne(@PathVariable @Valid Integer id){
        UserDto user = userService.getOneUser(String.valueOf(id));
        return ResponseEntity.ok().body(user);
    }

}
