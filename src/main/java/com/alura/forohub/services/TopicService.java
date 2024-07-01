package com.alura.forohub.services;

import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.model.Topic;
import com.alura.forohub.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository repository){
        this.topicRepository = repository;
    }

    @Transactional
    public ResponseEntity registerTopic(CreateTopicDto createTopicDto){
        var topic = new Topic(createTopicDto);
        Optional<Topic> topicDb;

        try{
           topicDb = Optional.of(topicRepository.save(topic));
        }catch (DataIntegrityViolationException e){
            String message = "Topico ya existe en la base de datos";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }
        var URI = UriComponentsBuilder.fromUriString("/topics/{id}").buildAndExpand(topicDb.get().getId()).toUri();

        return ResponseEntity.created(URI).body(topicDb.get());
    }
}
