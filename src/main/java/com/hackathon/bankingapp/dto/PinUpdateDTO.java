package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinUpdateDTO {

    @Pattern(regexp = "^\\d{4}$")
    private String oldPin;

    @NotNull
    private String password;

    @Pattern(regexp = "^\\d{4}$")
    private String newPin;
}
