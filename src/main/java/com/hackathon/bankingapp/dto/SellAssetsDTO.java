package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SellAssetsDTO {

    @NotNull
    private String assetSymbol;

    @NotNull
    private String pin;

    @NotNull
    private BigDecimal quantity;

}
