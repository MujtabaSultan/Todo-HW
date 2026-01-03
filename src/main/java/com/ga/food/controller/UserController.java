package com.ga.food.controller;

import com.ga.food.model.User;
import com.ga.food.model.request.LoginRequest;
import com.ga.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public User createUser(@RequestBody User userObj){
        System.out.println("calling create user ==========>");
        return userService.createUser(userObj);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.out.println("calling login request in service ==========>");
       return userService.loginUser(loginRequest);
    }
    @GetMapping("/reset-password")
    public void passwordReset(@RequestBody User user){
        System.out.println("calling reset in controller ========>");
        userService.resetPassword(user.getEmailAddress());
    }
    @PostMapping("/reset-password")
    public void passwordResetActivator(@RequestBody User user ,@RequestParam String token){
        System.out.println("calling reset activator in controller ========>");
        userService.resetPasswordActivator(token,user);
        //userService.resetPassword(user.getEmailAddress());

    }
}
