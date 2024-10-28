package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.DepositDTO;
import com.hackathon.bankingapp.dto.FundTransferDTO;
import com.hackathon.bankingapp.dto.WithdrawalDTO;
import com.hackathon.bankingapp.entities.Transaction;
import com.hackathon.bankingapp.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<Map<String, String>> deposit(@AuthenticationPrincipal UserDetails userDetails,
                                                       @Valid @RequestBody DepositDTO depositDTO){
        transactionService.deposit(userDetails.getUsername(), depositDTO);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash deposited successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/withdraw")
    public ResponseEntity<Map<String, String>> withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                                        @Valid @RequestBody WithdrawalDTO withdrawalDTO){
        transactionService.withdraw(userDetails.getUsername(), withdrawalDTO);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/fund-transfer")
    public ResponseEntity<Map<String, String>> fundTransfer(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid @RequestBody FundTransferDTO fundTransferDTO){
        transactionService.fundTransfer(userDetails.getUsername(), fundTransferDTO);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Fund transferred successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/transactions")
    public ResponseEntity<List<Transaction>> transactions(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(transactionService.getTransactions(userDetails.getUsername()));
    }






}
