package com.hackathon.bankingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String address;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountNumber;

    private String hashedPassword;

    @JsonIgnore
    private String pin;

}
