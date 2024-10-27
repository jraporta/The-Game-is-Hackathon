package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.PinCreateDTO;
import com.hackathon.bankingapp.dto.PinUpdateDTO;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.exceptions.InvalidPinException;
import com.hackathon.bankingapp.exceptions.UserAlreadyHasPinException;
import com.hackathon.bankingapp.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class PinService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public PinService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createPin(String accountNumber, PinCreateDTO pinCreateDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        if (!passwordEncoder.matches(pinCreateDTO.getPassword(),user.getHashedPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
        if (user.getPin() != null) throw new UserAlreadyHasPinException("User already has a PIN");
        user.setPin(pinCreateDTO.getPin());
        userRepository.save(user);
    }

    public void updatePin(String accountNumber, @Valid PinUpdateDTO pinUpdateDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        if (!passwordEncoder.matches(pinUpdateDTO.getPassword(),user.getHashedPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
        if (user.getPin() != null && !pinUpdateDTO.getOldPin().equals(user.getPin())){
            throw new InvalidPinException("Invalid Pin");
        }
        user.setPin(pinUpdateDTO.getNewPin());
        userRepository.save(user);
    }
}
