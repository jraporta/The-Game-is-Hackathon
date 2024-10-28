package com.hackathon.bankingapp.entities;

import com.hackathon.bankingapp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal amount;

    private TransactionType transactionType;

    private Date transactionDate;

    private String sourceAccountNumber;

    private String targetAccountNumber;

}
