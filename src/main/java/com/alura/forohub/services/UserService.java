package com.alura.forohub.services;


import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.model.User;
import com.alura.forohub.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    @Transactional
    public ResponseEntity registerUser(CreateUserDto createUserDto){
        var user = new User(createUserDto);
        Optional<User> userDb;

        try {
            userDb = Optional.of(userRepository.save(user));
        }catch (DataIntegrityViolationException e){
            String message = "Error de integridad de datos";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }catch (ConstraintViolationException e){
            String message = "Violacion de restricciones. Nombre y correo deben ser unicos. Transaccion fallida";
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }
        if (userDb.isEmpty()){
            String message = "Usuario vacio";
            logger.error(message);
            throw new RuntimeException(message);
        }

        final URI uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(userDb.get().getId()).toUri();

        return ResponseEntity.created(uri).body(userDb.get());

    }


    @Transactional
    public Optional<User> findByIdOrEmailOrName(String author){
            try {
                long id = Long.parseLong(author);
                return userRepository.findById(id);
            }catch (NumberFormatException e){
                String message = "Id autor no es un numero";
                logger.info(message);
                return Optional.ofNullable(userRepository.findByEmailOrName(author));
            }

    }
}
