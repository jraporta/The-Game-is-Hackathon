package com.hackathon.bankingapp.exceptions;

public class NonExistingIdentifierException extends RuntimeException{
    public NonExistingIdentifierException(String message) {
        super(message);
    }
}
