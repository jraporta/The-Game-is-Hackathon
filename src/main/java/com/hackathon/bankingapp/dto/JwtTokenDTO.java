package com.hackathon.bankingapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDTO {

    private String token;

    public JwtTokenDTO(String token) {
        this.token = token;
    }
}
