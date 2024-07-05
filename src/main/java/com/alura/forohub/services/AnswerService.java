package com.alura.forohub.services;

import com.alura.forohub.dto.AnswerDto;
import com.alura.forohub.dto.CreateAnswerDto;
import com.alura.forohub.dto.TopicDto;
import com.alura.forohub.dto.UserDto;
import com.alura.forohub.model.Answer;
import com.alura.forohub.model.Topic;
import com.alura.forohub.model.User;
import com.alura.forohub.repository.AnswerRepository;
import com.alura.forohub.utility.exceptions.NotFoundException;
import com.alura.forohub.utility.exceptions.RegisterException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerService.class);

    private final AnswerRepository answerRepository;

    private final UserService userService;

    private final ForoService foroService;
    @Autowired
    public AnswerService(AnswerRepository repository, UserService userService, ForoService foroService){
        this.answerRepository = repository;
        this.userService = userService;
        this.foroService = foroService;
    }

    @Transactional
    public AnswerDto registerAnswer(CreateAnswerDto createAnswerDto){
        var answer = new Answer(createAnswerDto);
        Optional<Answer> answerDb;

        UserDto user = userService.getOneUser(createAnswerDto.author());
        TopicDto topic = foroService.getOneTopic(createAnswerDto.topic());

        answer.setAuthor(new User(user));
        answer.setTopic(new Topic(topic));

        try{
            answerDb = Optional.of(answerRepository.save(answer));
        } catch (UnexpectedRollbackException | DataAccessException e){
            String message = "No fue posible registar la respuesta";
            logger.error(message);
            throw new RegisterException(message);
        }

        return new AnswerDto(answerDb.get());
    }

    @Transactional
    public AnswerDto getAnswerById(Integer id){
        try {
            return new AnswerDto(answerRepository.findById((long)id).orElseThrow());
        }catch (NoSuchElementException e){
            String message = "No se encontro una respuesta con el id " + id;
            logger.error(message);
            throw new NotFoundException(message);
        }
    }
}
