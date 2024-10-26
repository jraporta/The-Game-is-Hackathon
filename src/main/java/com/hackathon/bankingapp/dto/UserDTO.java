package com.hackathon.bankingapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;
}
