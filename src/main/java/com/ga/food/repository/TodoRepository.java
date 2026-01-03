package com.ga.food.repository;

import com.ga.food.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCategoryId(Long todoId);
    Todo findByName(String recipeName);
}
