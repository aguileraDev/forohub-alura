package com.alura.forohub.repository;

import com.alura.forohub.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByStatusTrue(Pageable pagination);
    Optional<Topic> findByIdAndStatusTrue(Long id);
}
