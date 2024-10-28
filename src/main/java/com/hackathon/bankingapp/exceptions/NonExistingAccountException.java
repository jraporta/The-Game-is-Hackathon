package com.hackathon.bankingapp.exceptions;

public class NonExistingAccountException extends RuntimeException{
    public NonExistingAccountException(String message) {
        super(message);
    }
}
