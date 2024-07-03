package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.dto.TopicDto;
import com.alura.forohub.services.ForoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/topics")
public class TopicController {

    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
    private final ForoService foroService;

    @Autowired
    public TopicController(ForoService service){
        this.foroService = service;

    }

    @PostMapping
    public ResponseEntity createTopic(@RequestBody @Valid CreateTopicDto createTopicDto){
        return foroService.registerTopic(createTopicDto);
    }

    @GetMapping
    public Page<TopicDto> getAllTopics(
            @PageableDefault(size = 10)
            Pageable pagination){
        return foroService.findAll(pagination);
    }
}
