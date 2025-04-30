package org.exercise.spring.spring_pizzeria.repository;

import java.util.Optional;

import org.exercise.spring.spring_pizzeria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
