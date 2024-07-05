package com.alura.forohub.controller;
import com.alura.forohub.dto.AnswerDto;
import com.alura.forohub.dto.CreateAnswerDto;
import com.alura.forohub.services.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<AnswerDto> createAnAnswer(@RequestBody @Valid CreateAnswerDto createAnswerDto){
        AnswerDto answer = answerService.registerAnswer(createAnswerDto);
        URI uri = UriComponentsBuilder.fromUriString("/answer/{id}").buildAndExpand(answer.id()).toUri();

        return ResponseEntity.created(uri).body(answer);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getAnswer(@PathVariable @Valid Integer id){
        AnswerDto answer = answerService.getAnswerById(id);

        return ResponseEntity.ok().body(answer);
    }
}
