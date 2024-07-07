package com.alura.forohub.services;


import com.alura.forohub.dto.CreateUserDto;
import com.alura.forohub.dto.UserDto;
import com.alura.forohub.model.User;
import com.alura.forohub.repository.UserRepository;
import com.alura.forohub.utility.exceptions.NotFoundException;
import com.alura.forohub.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
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
    public UserDto registerUser(CreateUserDto createUserDto){
        var user = new User(createUserDto);
        Optional<User> userDb;

        try {
            userDb = Optional.of(userRepository.save(user));
        }catch (DataIntegrityViolationException e){
            String message = "Error de integridad de datos";
            logger.error(message);
            throw new RegisterException(message);
        }catch (ConstraintViolationException e){
            String message = "Violacion de restricciones. Nombre y correo deben ser unicos. Transaccion fallida";
            logger.error(message);
            throw new RegisterException(message);
        }


        return new UserDto(userDb.get());

    }

    @Transactional
    public UserDto getOneUser(String author){
        try {
            return new UserDto(findByIdOrEmailOrName(author).orElseThrow());
        }catch (NoSuchElementException e){
            String message = "usuario no encontrado";
            throw new NotFoundException(message);
        }
    }

    @Transactional
    private Optional<User> findByIdOrEmailOrName(String author){

            try {
                long id = Long.parseLong(author);
                return userRepository.findById(id);
            }catch (NumberFormatException e){
                String message = "Id autor no es un numero";
                logger.info(message);
                return Optional.ofNullable(userRepository.findByEmailOrName(author));
            }
    }

    @Transactional
    public UserDetails getUserForLogin(String name){
        return userRepository.findByNameAndIsActiveTrue(name);
    }
}
