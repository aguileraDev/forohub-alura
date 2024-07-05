package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.dto.TopicDto;
import com.alura.forohub.dto.UpdateTopicDto;
import com.alura.forohub.services.ForoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<TopicDto> createTopic(@RequestBody @Valid CreateTopicDto createTopicDto){
        TopicDto topic = foroService.registerTopic(createTopicDto);

        URI uri = UriComponentsBuilder.fromUriString("/topics/{id}").buildAndExpand(topic.id()).toUri();

        return ResponseEntity.created(uri).body(topic);


    }

    @GetMapping
    public Page<TopicDto> getAllTopics(
            @PageableDefault(size = 10)
            Pageable pagination){
        return foroService.findAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable @Valid Integer id){
        var topic = foroService.getOneTopic(id);

        return ResponseEntity.ok().body(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateTopicDto updateTopicDto) {

        TopicDto topicDto = foroService.updateTopic(id, updateTopicDto);
        return ResponseEntity.ok().body(topicDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteTopic(@PathVariable @Valid Integer id){
        foroService.disableTopic(id);
        return ResponseEntity.noContent().build();
    }

}
