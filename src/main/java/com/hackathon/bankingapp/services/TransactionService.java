package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.DepositDTO;
import com.hackathon.bankingapp.dto.FundTransferDTO;
import com.hackathon.bankingapp.dto.WithdrawalDTO;
import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.Transaction;
import com.hackathon.bankingapp.entities.User;
import com.hackathon.bankingapp.enums.TransactionType;
import com.hackathon.bankingapp.exceptions.InsufficientFundsException;
import com.hackathon.bankingapp.exceptions.InvalidPinException;
import com.hackathon.bankingapp.exceptions.NonExistingAccountException;
import com.hackathon.bankingapp.repositories.AccountRepository;
import com.hackathon.bankingapp.repositories.TransactionRepository;
import com.hackathon.bankingapp.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {

    AccountRepository accountRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, UserRepository userRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(String accountNumber, DepositDTO depositDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account account = accountRepository.findByUser(user);
        BigDecimal amount = new BigDecimal(depositDTO.getAmount());
        if (!user.getPin().equals(depositDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        depositIntoAccount(account, amount);
        saveTransaction(amount, TransactionType.CASH_DEPOSIT, account, null);
    }

    public void withdraw(String accountNumber, WithdrawalDTO withdrawalDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account account = accountRepository.findByUser(user);
        BigDecimal amount = withdrawalDTO.getAmount();
        if (!user.getPin().equals(withdrawalDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        if (account.getBalance().compareTo(withdrawalDTO.getAmount()) < 0){
            throw new InsufficientFundsException("Insufficient balance");
        }
        withdrawFromAccount(account, amount);
        saveTransaction(amount, TransactionType.CASH_WITHDRAWAL, account, null);
    }

    public void fundTransfer(String accountNumber, @Valid FundTransferDTO fundTransferDTO) {
        User user = userRepository.findByAccountNumber(accountNumber);
        Account sourceAccount = accountRepository.findByUser(user);
        BigDecimal amount = fundTransferDTO.getAmount();
        if (!user.getPin().equals(fundTransferDTO.getPin())) throw new InvalidPinException("Invalid PIN");
        if (sourceAccount.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient balance");
        }
        Account targetAccount = accountRepository.findByUser(userRepository
                .findByAccountNumber(fundTransferDTO.getTargetAccountNumber()));
        if (targetAccount == null) throw new NonExistingAccountException("");
        withdrawFromAccount(sourceAccount, amount);
        depositIntoAccount(targetAccount, amount);
        saveTransaction(amount, TransactionType.CASH_TRANSFER, sourceAccount, targetAccount);
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

    private void saveTransaction(BigDecimal amount, TransactionType type, Account sourceAccount, Account targetAccount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setSourceAccountNumber(sourceAccount.getUser().getAccountNumber());
        transaction.setTargetAccountNumber(targetAccount == null ? "N/A" : targetAccount.getUser().getAccountNumber());
        transaction.setTransactionDate(new Date(System.currentTimeMillis()));
        transactionRepository.save(transaction);
        addTransaction(sourceAccount, transaction);
        if (targetAccount != null) addTransaction(targetAccount, transaction);
    }

    private void addTransaction(Account account, Transaction transaction) {
        account.getTransactions().add(transaction);
        accountRepository.save((account));
    }
}
