package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.DepositDTO;
import com.hackathon.bankingapp.dto.FundTransferDTO;
import com.hackathon.bankingapp.dto.WithdrawalDTO;
import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.exceptions.InsufficientFundsException;
import com.hackathon.bankingapp.exceptions.InvalidPinException;
import com.hackathon.bankingapp.exceptions.NonExistingAccountException;
import com.hackathon.bankingapp.repositories.AccountRepository;
import com.hackathon.bankingapp.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
        depositIntoAccount(account, new BigDecimal(depositDTO.getAmount()));
    }

    public void withdraw(String accountNumber, WithdrawalDTO withdrawalDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account account = accountRepository.findByUser(user);
        if (!user.getPin().equals(withdrawalDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        if (account.getBalance().compareTo(withdrawalDTO.getAmount()) < 0){
            throw new InsufficientFundsException("Insufficient balance");
        }
        withdrawFromAccount(account, withdrawalDTO.getAmount());
    }

    public void fundTransfer(String accountNumber, @Valid FundTransferDTO fundTransferDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account sourceAccount = accountRepository.findByUser(user);
        if (!user.getPin().equals(fundTransferDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        if (sourceAccount.getBalance().compareTo(fundTransferDTO.getAmount()) < 0){
            throw new InsufficientFundsException("Insufficient balance");
        }
        Account targetAccount = accountRepository.findByUser(userRepository
                .findByAccountNumber(fundTransferDTO.getTargetAccountNumber()));
        if (targetAccount == null) throw new NonExistingAccountException("");
        withdrawFromAccount(sourceAccount, fundTransferDTO.getAmount());
        depositIntoAccount(targetAccount, fundTransferDTO.getAmount());
    }

    private void depositIntoAccount(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void withdrawFromAccount(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
