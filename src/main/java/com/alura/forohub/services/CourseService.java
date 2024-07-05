package com.alura.forohub.services;

import com.alura.forohub.dto.CourseDto;
import com.alura.forohub.dto.CreateCourseDto;
import com.alura.forohub.model.Course;
import com.alura.forohub.repository.CourseRepository;
import com.alura.forohub.utility.exceptions.NotFoundException;
import com.alura.forohub.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.NoSuchElementException;
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
    public CourseDto registerCourse(CreateCourseDto createCourseDto){
        var course = new Course(createCourseDto);
        Optional<Course> courseDb;

        try{
            courseDb = Optional.of(courseRepository.save(course));
        }catch (DataIntegrityViolationException e){
            String message = "Curso no cumple restricciones o ya existe en la base de datos";
            logger.error(message);
            throw new RegisterException(message);
        }

        return new CourseDto(courseDb.get());
    }

    @Transactional
    public CourseDto getCourseById(Integer id){
        try{
            return new CourseDto(courseRepository.findById((long) id).orElseThrow());
        }catch (NoSuchElementException e){
            String message = String.format("Curso con el id %d no encontrado",id);
            logger.error(message);
            throw new  NotFoundException(message);
        }
    }
}
