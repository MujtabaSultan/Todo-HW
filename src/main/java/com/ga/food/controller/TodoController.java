package com.ga.food.controller;

import com.ga.food.model.Todo;
import com.ga.food.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class TodoController {
    TodoService todoService;
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
    @GetMapping("/categories/{categoryId}/todo")
    public List<Todo> getTodoList(@PathVariable Long categoryId){

        return todoService.getTodoList(categoryId);
    }
    @PostMapping("/categories/{categoryId}/todo")
    public Todo createTodo(@RequestBody Todo todoObj, @PathVariable Long categoryId){
        System.out.println("->>>>>>>>>>>>" + categoryId);
        return todoService.createTodo(todoObj,categoryId);

    }
    @GetMapping("/categories/{categoryId}/todo/{todoId}")
    public Todo getOneTodo(@PathVariable Long todoId ){
        return todoService.getOneTodo(todoId);
    }
    @DeleteMapping("/categories/{categoryId}/todo/{todoId}")
    public Todo deleteTodo(@PathVariable Long todoId ){
        return todoService.deleteTodo(todoId);
    }
    @PutMapping("/categories/{categoryId}/todo/{todoId}")
    public Todo updateTodo(@PathVariable Long todoId , @RequestBody Todo todo){
        return todoService.updateTodo(todo,todoId);
    }

}
