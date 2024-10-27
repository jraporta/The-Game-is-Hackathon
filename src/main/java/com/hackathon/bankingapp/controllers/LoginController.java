package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.JwtTokenDTO;
import com.hackathon.bankingapp.dto.LoginDTO;
import com.hackathon.bankingapp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<JwtTokenDTO> createAuthenticationToken(@RequestBody LoginDTO credentials){
        UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getIdentifier());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getIdentifier(), credentials.getPassword()));
        String token = jwtUtil.generateToken((userDetails.getUsername()));
        log.info("created token for: {}", jwtUtil.extractUsername(token));
        return ResponseEntity.ok(new JwtTokenDTO(token));
    }


}
