package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.exceptions.NonExistingIdentifierException;
import com.hackathon.bankingapp.repositories.UserRepository;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private UserRepository userRepository;
    private final MailSender mailSender;

    public PasswordResetService(UserRepository userRepository, MailSender mailSender){
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public void resetPassword(String email) {
        if (!userRepository.existsByEmail(email)) throw new NonExistingIdentifierException("");
        sendMail(email, "Password Reset", "OTP:416231");
    }

    public void sendMail(String to, String subject, String text){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        mailSender.send(email);
    }
}
