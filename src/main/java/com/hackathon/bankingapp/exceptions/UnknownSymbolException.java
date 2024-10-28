package com.hackathon.bankingapp.exceptions;

public class UnknownSymbolException extends RuntimeException{
    public UnknownSymbolException(String message) {
        super(message);
    }
}
