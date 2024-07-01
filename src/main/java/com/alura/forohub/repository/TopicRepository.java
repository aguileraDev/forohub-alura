package com.alura.forohub.repository;

import com.alura.forohub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
