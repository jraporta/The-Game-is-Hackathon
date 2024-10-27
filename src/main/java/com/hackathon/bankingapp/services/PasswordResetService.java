package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.exceptions.NonExistingIdentifierException;
import com.hackathon.bankingapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private UserRepository userRepository;

    public PasswordResetService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void resetPassword(String email) {
        if (!userRepository.existsByEmail(email)) throw new NonExistingIdentifierException("");
    }
}
