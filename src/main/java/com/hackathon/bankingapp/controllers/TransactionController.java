package com.hackathon.bankingapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @PostMapping("/account/deposit")
    public ResponseEntity<Map<String, String>> deposit(@AuthenticationPrincipal UserDetails userDetails){

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash deposited successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/withdraw")
    public ResponseEntity<Map<String, String>> withdraw(@AuthenticationPrincipal UserDetails userDetails){

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account/fund-transfer")
    public ResponseEntity<Map<String, String>> fundTransfer(@AuthenticationPrincipal UserDetails userDetails){

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Fund transferred successfully");
        return ResponseEntity.ok(response);
    }

    /*
    @PostMapping("/account/transactions")
    public void transactions(@AuthenticationPrincipal UserDetails userDetails){

    }

     */




}
