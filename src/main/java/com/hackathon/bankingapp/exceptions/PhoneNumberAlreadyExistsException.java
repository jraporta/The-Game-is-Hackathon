package com.hackathon.bankingapp.exceptions;

public class PhoneNumberAlreadyExistsException extends RuntimeException{
    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}
