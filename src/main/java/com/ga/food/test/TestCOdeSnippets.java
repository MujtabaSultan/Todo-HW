package com.ga.food.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCOdeSnippets {
    @GetMapping("/Hello")
    public String hello(){
        System.out.println("Out App is working bad...");
        return "All bad so Far";
    }
}
