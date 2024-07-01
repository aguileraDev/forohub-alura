package com.alura.forohub.model;

import com.alura.forohub.dto.CreateTopicDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Topic implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    private String message;
    private Boolean status;
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
