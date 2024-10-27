package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    @NotNull
    private String identifier;

    @NotNull
    private String resetToken;

    @NotNull
    private String newPassword;

}
