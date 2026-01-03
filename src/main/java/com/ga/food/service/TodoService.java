package com.ga.food.service;

import com.ga.food.exception.InformationNotFoundException;
import com.ga.food.model.Category;
import com.ga.food.model.Todo;
import com.ga.food.repository.CategoryRepository;
import com.ga.food.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    TodoRepository todoRepository;
    CategoryRepository categoryRepository;
    public TodoService(TodoRepository todoRepository, CategoryRepository categoryRepository){
        this.todoRepository = todoRepository;
        this.categoryRepository=categoryRepository;

    }

    public List<Todo> getTodoList(Long id) {
       return todoRepository.findByCategoryId(id);
    }

    public Todo createTodo(Todo todoObj, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new InformationNotFoundException("not found this category"));
        todoObj.setCategory(category);
       return todoRepository.save(todoObj);
    }

    public Todo getOneTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(()->new InformationNotFoundException("todo id not found"));
    }

    public Todo deleteTodo(Long todoId) {
        Todo found = todoRepository.findById(todoId).orElseThrow(()->new InformationNotFoundException("todo id not found"));
        todoRepository.deleteById(todoId);
        return found;
    }

    public Todo updateTodo(Todo todo, Long todoId) {
        todo.setId(todoId);
        return todoRepository.save(todo);
    }
}
