package com.alura.forohub.repository;

import com.alura.forohub.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
