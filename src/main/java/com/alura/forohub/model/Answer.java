package com.alura.forohub.model;

import com.alura.forohub.dto.AnswerDto;
import com.alura.forohub.dto.CreateAnswerDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Manuel Aguilera / @aguileradev
 */

@Entity
@Table(name = "responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer implements Serializable{

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;
    private Boolean solution;
    @ManyToOne()
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;

    private Instant created_at;

    public Answer(CreateAnswerDto createAnswerDto){
        this.message = createAnswerDto.message();
        this.solution = false;
        try {
            this.created_at = ZonedDateTime.now(ZoneId.of("America/Bogota")).toInstant();
        }catch (DateTimeException e){
            this.created_at = Instant.now();
        }
    }

    public Answer(AnswerDto answerDto){
        this.id = answerDto.id();
        this.message = answerDto.message();
        this.solution = answerDto.solution();
        this.created_at = answerDto.created_at();

    }

    public Answer(AnswerDto answerDto, User author, Topic topic){
        this.id = answerDto.id();
        this.message = answerDto.message();
        this.solution = answerDto.solution();
        this.created_at = answerDto.created_at();
        this.author = author;
        this.topic = topic;
    }
}
