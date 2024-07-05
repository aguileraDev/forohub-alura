package com.alura.forohub.controller;

import com.alura.forohub.utility.exceptions.NotFoundException;
import com.alura.forohub.utility.exceptions.RegisterException;
import com.alura.forohub.utility.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFound(ResourceNotFoundException e){
        String message = e.getMessage();
        logger.error(message);
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity registerFailed(RegisterException e){
        String message = e.getMessage();
        logger.error(message);
        return ResponseEntity.badRequest().body(message);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerInvalidFields(MethodArgumentNotValidException e){
        var errors = e.getFieldErrors().stream().map(ExceptionFieldErrors::new).toList();
        logger.error("Campos invalidos " + errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity notFoundElement(NoSuchElementException e){
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundEntity(NotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity databaseQueryErrorHandler(SQLException  e){
        String message = "Ha ocurrido un error con la consulta hacia la base de datos "+ e.getMessage();
        logger.error(message);
        return ResponseEntity.internalServerError().body(message);

    }
    public record ExceptionFieldErrors(String field, String message){
        public ExceptionFieldErrors(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
