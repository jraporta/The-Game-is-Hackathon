package com.hackathon.bankingapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal balance = new BigDecimal(0);

    @OneToOne
    @JoinColumn(name = "account_number", referencedColumnName = "accountNumber")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "account_has_transactions",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private Set<Transaction> transactions = new HashSet<>();

}
