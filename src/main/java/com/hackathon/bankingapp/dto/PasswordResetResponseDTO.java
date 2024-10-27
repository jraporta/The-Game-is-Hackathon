package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetResponseDTO {

    private String message;

    public PasswordResetResponseDTO(String message) {
        this.message = message;
    }
}
