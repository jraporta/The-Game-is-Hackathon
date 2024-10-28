package com.hackathon.bankingapp.utils;

import com.hackathon.bankingapp.exceptions.FormatException;
import org.springframework.stereotype.Component;

@Component
public class PasswordFormatValidator {

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void validate() throws FormatException {
        if (!password.matches("^.*[A-Z]*$")) {
            throw new FormatException("Password must contain at least one uppercase letter");
        }
        if (!password.matches("^.*\\d.*$")) {
            throw new FormatException("Password must contain at least one digit");
        }
        if (!password.matches("^.*[^\\p{Alnum}].*$")) {
            throw new FormatException("Password must contain at least one special character");
        }
        if (password.matches(".*\\s.*")) {
            throw new FormatException("Password cannot contain whitespace");
        }
        if (password.length() < 8) {
            throw new FormatException("Password must be at least 8 characters long");
        }
        if (password.length() > 127) {
            throw new FormatException("Password must be less than 128 characters long");
        }
    }

}
