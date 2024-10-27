package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.UserDTO;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    private UserService userService;

    public Controller(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO){
        User newUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(newUser);
    }




}
