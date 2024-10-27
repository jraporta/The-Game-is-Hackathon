package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDTO {

    @NotNull
    private String identifier;

    @NotNull
    private String otp;

}