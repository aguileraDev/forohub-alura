package com.alura.forohub.services;

import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.model.Course;
import com.alura.forohub.repository.CourseRepository;
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
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }
    @Transactional
    public ResponseEntity registerCourse(CreateCourseDto createCourseDto){
        var course = new Course(createCourseDto);
        Optional<Course> courseDb;

        try{
            courseDb = Optional.of(courseRepository.save(course));
        }catch (DataIntegrityViolationException e){
            String message = "Curso no cumple restricciones o ya existe en la base de datos";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }

        var uri = UriComponentsBuilder.fromUriString("/courses/{id}").buildAndExpand(courseDb.get().getId()).toUri();

        return ResponseEntity.created(uri).body(courseDb.get());
    }

    @Transactional
    public Optional<Course> getCourseById(Integer id){
        return courseRepository.findById((long) id);
    }
}
