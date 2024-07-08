package com.alura.forohub.controller;

import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.dto.TopicDto;
import com.alura.forohub.dto.UpdateTopicDto;
import com.alura.forohub.services.ForoService;
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
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/topics")
public class TopicController {

    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
    private final ForoService foroService;

    @Autowired
    public TopicController(ForoService service){
        this.foroService = service;

    }
    @Operation(summary = "Create a new topic")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Topic created",
                    content = {
                    @Content(
                            mediaType = "application/json" ,
                            schema = @Schema(implementation = TopicDto.class))
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
    public ResponseEntity<TopicDto> createTopic(@RequestBody @Valid CreateTopicDto createTopicDto){
        TopicDto topic = foroService.registerTopic(createTopicDto);

        URI uri = UriComponentsBuilder.fromUriString("/topics/{id}").buildAndExpand(topic.id()).toUri();

        return ResponseEntity.created(uri).body(topic);


    }
    @Operation(summary = "get all topics with pagination")
    @ApiResponse(
            responseCode = "200",
            description ="page with all topics. page size = 10",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))
            }
    )
    @GetMapping
    public Page<TopicDto> getAllTopics(
            @PageableDefault(size = 10)
            Pageable pagination){
        return foroService.findAll(pagination);
    }

    @Operation(summary = "Get one topic by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "topic obtained",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = TopicDto.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "topic not found",
                    content = {@Content}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable @Valid Integer id){
        var topic = foroService.getOneTopic(id);

        return ResponseEntity.ok().body(topic);
    }

    @Operation(summary = "Update a topic")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "topic updated",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    schema = @Schema(implementation = TopicDto.class))
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
    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateTopicDto updateTopicDto) {

        TopicDto topicDto = foroService.updateTopic(id, updateTopicDto);
        return ResponseEntity.ok().body(topicDto);
    }

    @Operation(summary = "changes the status of a topic. soft delete")
    @ApiResponses(value = {
     @ApiResponse(responseCode = "204", description = "status changed to false", content = {@Content}),
     @ApiResponse(responseCode = "404", description = "topic not found", content = {@Content})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteTopic(@PathVariable @Valid Integer id){
        foroService.disableTopic(id);
        return ResponseEntity.noContent().build();
    }

}
