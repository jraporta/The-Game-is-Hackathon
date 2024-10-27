package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PinUpdateDTO {

    private String oldPin;

    private String password;

    private String newPin;
}
