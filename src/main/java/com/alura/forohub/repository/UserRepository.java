package com.alura.forohub.repository;

import com.alura.forohub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


/**
 * @author Manuel Aguilera / @aguileradev
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where lower(u.name)=lower(:value) or lower(u.email) = lower(:value)")
    User findByEmailOrName(@Param("value") String value);

    UserDetails findByNameAndIsActiveTrue(String name);
}
