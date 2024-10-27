package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.AccountDTO;
import com.hackathon.bankingapp.dto.UserDTO;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.services.BlacklistService;
import com.hackathon.bankingapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    private UserService userService;
    private BlacklistService blacklistService;

    public Controller(UserService userService, BlacklistService blacklistService){
        this.userService = userService;
        this.blacklistService = blacklistService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDTO userDTO){
        User newUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/dashboard/user")
    public ResponseEntity<User> getLoggedUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
    }

    @GetMapping("/dashboard/account")
    public ResponseEntity<AccountDTO> getAccountInfo(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(userService.getAccountInfo(userDetails.getUsername()));
    }

    @GetMapping("/users/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.substring(7);
        blacklistService.addToken(token);
        return ResponseEntity.ok("");
    }

}
