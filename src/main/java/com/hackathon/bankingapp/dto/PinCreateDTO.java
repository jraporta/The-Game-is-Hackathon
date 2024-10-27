package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinCreateDTO {

    @NotNull
    private String pin;

    @NotNull
    private String password;


}
