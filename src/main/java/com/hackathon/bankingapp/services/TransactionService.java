package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.DepositDTO;
import com.hackathon.bankingapp.dto.WithdrawalDTO;
import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.exceptions.InsufficientFundsException;
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

    public void withdraw(String accountNumber, WithdrawalDTO withdrawalDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account account = accountRepository.findByUser(user);
        if (!user.getPin().equals(withdrawalDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        if (account.getBalance().compareTo(withdrawalDTO.getAmount()) < 0){
            throw new InsufficientFundsException("Insufficient balance");
        }
        BigDecimal newBalance = account.getBalance().subtract(withdrawalDTO.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
