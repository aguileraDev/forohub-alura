package com.alura.forohub.services;


import com.alura.forohub.dto.*;
import com.alura.forohub.model.Course;
import com.alura.forohub.model.Topic;
import com.alura.forohub.model.User;
import com.alura.forohub.repository.TopicRepository;
import com.alura.forohub.utility.exceptions.NotFoundException;
import com.alura.forohub.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    public TopicDto registerTopic(CreateTopicDto createTopicDto){
        var topic = new Topic(createTopicDto);
        Optional<Topic> topicDb;

        UserDto user = userService.getOneUser(createTopicDto.author());
        CourseDto course = courseService.getCourseById(createTopicDto.course());

        topic.setAuthor(new User(user));
        topic.setCourse(new Course(course));

        try{
           topicDb = Optional.of(topicRepository.save(topic));
        }catch (DataIntegrityViolationException e){
            String message = "Topico no cumple restricciones o ya existe en la base de datos";
            logger.error(message);
            throw new RegisterException(message);
        }


        return new TopicDto(topicDb.get());
    }

    @Transactional
    public Page<TopicDto> findAll(Pageable pagination){
        return topicRepository.findByStatusTrue(pagination).map(TopicDto::new);
    }

    @Transactional
    public TopicDto getOneTopic(Integer id){
        Topic topic;
        try{
            topic = topicRepository.findByIdAndStatusTrue((long)id).orElseThrow();
        }catch (NoSuchElementException e){
            String message = String.format("Topico con el id %d no encontrado",id);
            throw new NotFoundException(message);
        }

        return new TopicDto(topic);
    }

    @Transactional
    public TopicDto updateTopic(Integer id, UpdateTopicDto updateTopicDto){
        Topic topic = new Topic(getOneTopic(id));
        topic.setId((long)id);

        if(updateTopicDto.message() != null){
            topic.setMessage(updateTopicDto.message());
        }

        if(updateTopicDto.title() != null){
            topic.setTitle(updateTopicDto.title());
        }

        if(updateTopicDto.author() != null){
            topic.setAuthor(new User(userService.getOneUser(updateTopicDto.author())));
        }

        if(updateTopicDto.course() != null){
            topic.setCourse(new Course(courseService.getCourseById(updateTopicDto.course())));
        }

        topicRepository.save(topic);

        return new TopicDto(topic);
    }

    @Transactional
    public void disableTopic(Integer id){
        Topic topic = new Topic(getOneTopic(id));
        topic.setStatus(false);
        topicRepository.save(topic);

    }

}
