package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BuyAssetsDTO {

    @NotNull
    private String assetSymbol;

    @NotNull
    private String pin;

    @NotNull
    private BigDecimal amount;

}