package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.PasswordResetDTO;
import com.hackathon.bankingapp.dto.PasswordResetResponseDTO;
import com.hackathon.bankingapp.dto.PasswordResetTokenDTO;
import com.hackathon.bankingapp.dto.VerifyOtpDTO;
import com.hackathon.bankingapp.services.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PasswordResetTokenDTO> verifyOTP(@RequestBody VerifyOtpDTO verifyOtpDTO){
        return ResponseEntity.ok(
                passwordResetService.verifyOTP(verifyOtpDTO.getIdentifier(), verifyOtpDTO.getOtp()));
    }

    @PostMapping("/auth/password-reset")
    public ResponseEntity<String> changePassword(){
        return ResponseEntity.ok("");
    }
}