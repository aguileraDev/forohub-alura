package com.alura.forohub.model;

import com.alura.forohub.dto.AnswerDto;
import com.alura.forohub.dto.CreateTopicDto;
import com.alura.forohub.dto.TopicDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Manuel Aguilera / @aguileradev
 */
@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String message;

    private Boolean status;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    @JsonManagedReference
    private User author;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    @JsonManagedReference
    private Course course;

    @OneToMany(mappedBy = "topic",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    private Instant created_at;


    public Topic(CreateTopicDto createTopicDto){
        this.title = createTopicDto.title();
        this.message = createTopicDto.message();
        this.status = true;
        try{
            this.created_at = ZonedDateTime.now(ZoneId.of("America/Bogota")).toInstant();

        }catch (DateTimeException e){
            this.created_at = Instant.now();
        }


    }

    public Topic(TopicDto topicDto) {
        this.id = topicDto.id();
        this.title = topicDto.title();
        this.message = topicDto.message();
        this.status = topicDto.status();
        this.created_at = topicDto.created_at();
        this.author = new User(topicDto.author());
        this.course = new Course(topicDto.course());
        this.answers = topicDto.answers() != null ? topicDto.answers().stream().map(
                answerDto -> new Answer(answerDto,this.author,this)).toList() : new ArrayList<>();
    }
}
