package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.entities.InvalidToken;
import com.hackathon.bankingapp.repositories.InvalidTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlacklistService {

    private final InvalidTokenRepository invalidTokenRepository;

    public BlacklistService(InvalidTokenRepository invalidTokenRepository) {
        this.invalidTokenRepository = invalidTokenRepository;
    }

    public void addToken(String token) {
        invalidTokenRepository.save(new InvalidToken(token));
        log.info("User logout, added token to blacklist.");
    }

    public boolean tokenIsValid(String jwt) {
        return !invalidTokenRepository.existsByToken(jwt);
    }
}
