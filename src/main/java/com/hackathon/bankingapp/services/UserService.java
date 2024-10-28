package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.AccountDTO;
import com.hackathon.bankingapp.dto.UserDTO;
import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.exceptions.EmailAlreadyExistsException;
import com.hackathon.bankingapp.exceptions.PhoneNumberAlreadyExistsException;
import com.hackathon.bankingapp.repositories.AccountRepository;
import com.hackathon.bankingapp.repositories.UserRepository;
import com.hackathon.bankingapp.utils.PasswordFormatValidator;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;
    private PasswordFormatValidator passwordFormatValidator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AccountRepository accountRepository, PasswordFormatValidator passwordFormatValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.passwordFormatValidator = passwordFormatValidator;
    }

    public User registerUser(@Valid UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists");
        }
        passwordFormatValidator.setPassword(userDTO.getPassword());
        passwordFormatValidator.validate();
        User user = saveUser(userDTO);
        createAccount(user);
        return user;
    }

    private void createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        accountRepository.save(account);
    }

    private User saveUser(@Valid UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setHashedPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(String accountNumber) {
        return userRepository.findByAccountNumber(accountNumber);
    }

    public AccountDTO getAccountInfo(String accountNumber) {
        User user = getUser(accountNumber);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber(user.getAccountNumber());
        accountDTO.setBalance(new BigDecimal(0));
        return accountDTO;
    }
}
