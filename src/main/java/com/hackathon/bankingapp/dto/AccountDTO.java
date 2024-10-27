package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {

    private String accountNumber;

    private BigDecimal balance;

}
