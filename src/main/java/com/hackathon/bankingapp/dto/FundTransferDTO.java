package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FundTransferDTO {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String pin;

    @NotNull
    private String targetAccountNumber;

}
