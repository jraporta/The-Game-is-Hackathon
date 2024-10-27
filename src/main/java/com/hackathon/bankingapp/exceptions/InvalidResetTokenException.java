package com.hackathon.bankingapp.exceptions;

public class InvalidResetTokenException extends RuntimeException{
    public InvalidResetTokenException(String message) {
        super(message);
    }
}
