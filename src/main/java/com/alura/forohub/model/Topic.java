package com.alura.forohub.model;

import com.alura.forohub.dto.CreateTopicDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
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
}
