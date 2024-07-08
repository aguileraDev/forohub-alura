package com.alura.forohub.controller;
import com.alura.forohub.dto.AnswerDto;
import com.alura.forohub.dto.CreateAnswerDto;
import com.alura.forohub.services.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.answerService = answerService;
    }
    @Operation(summary = "Register a new response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Answer created",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = AnswerDto.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid json data. fields missing",
                    content = {@Content}),
            @ApiResponse(
                    responseCode = "404",
                    description = "author or course not found",
                    content = {@Content}
            )
    })
    @PostMapping
    public ResponseEntity<AnswerDto> createAnAnswer(@RequestBody @Valid CreateAnswerDto createAnswerDto){
        AnswerDto answer = answerService.registerAnswer(createAnswerDto);
        URI uri = UriComponentsBuilder.fromUriString("/answer/{id}").buildAndExpand(answer.id()).toUri();

        return ResponseEntity.created(uri).body(answer);

    }

    @Operation(summary = "Get one answer")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "response obtained",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = AnswerDto.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "response not found",
                    content = {@Content}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getAnswer(@PathVariable @Valid Integer id){
        AnswerDto answer = answerService.getAnswerById(id);

        return ResponseEntity.ok().body(answer);
    }
}
