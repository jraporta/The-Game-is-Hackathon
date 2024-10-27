package com.hackathon.bankingapp.exceptions;

public class UserAlreadyHasPinException extends RuntimeException{
    public UserAlreadyHasPinException(String message) {
        super(message);
    }
}
