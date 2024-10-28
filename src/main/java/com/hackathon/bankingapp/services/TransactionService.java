package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.DepositDTO;
import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.exceptions.InvalidPinException;
import com.hackathon.bankingapp.repositories.AccountRepository;
import com.hackathon.bankingapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    AccountRepository accountRepository;
    UserRepository userRepository;

    public TransactionService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public void deposit(String accountNumber, DepositDTO depositDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account account = accountRepository.findByUser(user);
        if (!user.getPin().equals(depositDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        BigDecimal newBalance = account.getBalance().add(new BigDecimal(depositDTO.getAmount()));
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
