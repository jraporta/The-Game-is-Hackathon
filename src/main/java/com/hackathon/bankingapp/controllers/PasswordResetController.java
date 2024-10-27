package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.PasswordResetDTO;
import com.hackathon.bankingapp.dto.PasswordResetResponseDTO;
import com.hackathon.bankingapp.dto.VerifyOtpDTO;
import com.hackathon.bankingapp.services.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;


    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/auth/password-reset/send-otp")
    public ResponseEntity<PasswordResetResponseDTO> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        passwordResetService.resetPassword(passwordResetDTO.getIdentifier());
        return ResponseEntity.ok(new PasswordResetResponseDTO(
                "OTP sent successfully to: " + passwordResetDTO.getIdentifier()));
    }

    @PostMapping("/auth/password-reset/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOTP(@Valid @RequestBody VerifyOtpDTO verifyOtpDTO){
        String token = passwordResetService.verifyOTP(verifyOtpDTO);
        Map<String, String> response = new HashMap<>();
        response.put("passwordResetToken", token);
        return ResponseEntity.ok(response);
    }



}