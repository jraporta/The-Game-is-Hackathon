package com.hackathon.bankingapp.exceptions;

public class InvalidOtpException extends RuntimeException{
    public InvalidOtpException(String message) {
        super(message);
    }
}
