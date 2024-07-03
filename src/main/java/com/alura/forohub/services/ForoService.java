package com.alura.forohub.services;

import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.dto.TopicDto;
import com.alura.forohub.model.Course;
import com.alura.forohub.model.Topic;
import com.alura.forohub.repository.CourseRepository;
import com.alura.forohub.repository.TopicRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class ForoService {

    private static final Logger logger = LoggerFactory.getLogger(ForoService.class);
    private final TopicRepository topicRepository;
    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public ForoService(TopicRepository topicRepository,
                       CourseService courseService,
                       UserService userService){

        this.topicRepository = topicRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Transactional
    public ResponseEntity registerTopic(CreateTopicDto createTopicDto){
        var topic = new Topic(createTopicDto);
        Optional<Topic> topicDb;

        var user = userService.findByIdOrEmailOrName(createTopicDto.author());
        var course = courseService.getCourseById(createTopicDto.course());

        if(user.isEmpty() || course.isEmpty()){
            String message = "Usuario o curso no encontrado";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }

        topic.setAuthor(user.get());
        topic.setCourse(course.get());

        try{
           topicDb = Optional.of(topicRepository.save(topic));
        }catch (DataIntegrityViolationException e){
            String message = "Topico no cumple restricciones o ya existe en la base de datos";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }
        var URI = UriComponentsBuilder.fromUriString("/topics/{id}").buildAndExpand(topicDb.get().getId()).toUri();

        return ResponseEntity.created(URI).body(new TopicDto(topicDb.get()));
    }

    @Transactional
    public Page<TopicDto> findAll(Pageable pagination){
        return topicRepository.findByStatusTrue(pagination).map(TopicDto::new);
    }

}
