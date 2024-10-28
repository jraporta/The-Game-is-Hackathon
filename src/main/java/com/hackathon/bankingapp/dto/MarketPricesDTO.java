package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MarketPricesDTO {

    @NotNull
    private Map<String, BigDecimal> assets;

}
