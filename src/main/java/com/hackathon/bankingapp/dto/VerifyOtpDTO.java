package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDTO {

    private String identifier;

    private String otp;

}
