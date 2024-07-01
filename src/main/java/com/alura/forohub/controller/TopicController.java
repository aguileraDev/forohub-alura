package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.services.TopicService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestController
@RequestMapping("/topics")
public class TopicController {

    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService service){
        this.topicService = service;

    }

    @PostMapping
    public ResponseEntity createTopic(@RequestBody @Valid CreateTopicDto createTopicDto){
        return topicService.registerTopic(createTopicDto);
    }
}
