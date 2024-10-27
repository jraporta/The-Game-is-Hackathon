package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinCreateDTO {

    @Pattern(regexp = "^\\d{4}$")
    private String pin;

    @NotNull
    private String password;


}
