package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetTokenDTO {

    private String passwordResetToken;

    public PasswordResetTokenDTO(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
