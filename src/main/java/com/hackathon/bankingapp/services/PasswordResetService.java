package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.PasswordResetTokenDTO;
import com.hackathon.bankingapp.entities.OneTimePassword;
import com.hackathon.bankingapp.exceptions.InvalidOtpException;
import com.hackathon.bankingapp.exceptions.NonExistingIdentifierException;
import com.hackathon.bankingapp.repositories.OneTimePasswordRepository;
import com.hackathon.bankingapp.repositories.UserRepository;
import com.hackathon.bankingapp.utils.JwtUtil;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final UserRepository userRepository;
    private final MailSender mailSender;
    private final JwtUtil jwtUtil;

    public PasswordResetService(UserRepository userRepository, MailSender mailSender,
                                OneTimePasswordRepository oneTimePasswordRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.jwtUtil = jwtUtil;
    }

    public void resetPassword(String email) {
        if (!userRepository.existsByEmail(email)) throw new NonExistingIdentifierException("");
        //OneTimePassword oldOtp = oneTimePasswordRepository.findByEmail(email);
        //if (oldOtp != null ) oneTimePasswordRepository.delete(oldOtp);
        int oneTimePassword = (int) (Math.random() * 100000);
        oneTimePasswordRepository.save(new OneTimePassword(email, Integer.toString(oneTimePassword)));
        sendMail(email, "Password Reset", "OTP:" + oneTimePassword);
    }

    public void sendMail(String to, String subject, String text){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        mailSender.send(email);
    }

    public PasswordResetTokenDTO verifyOTP(String email, String otp) {
        OneTimePassword oneTimePassword = oneTimePasswordRepository.findByEmail(email);
        if (oneTimePassword != null && oneTimePassword.getOtp().equals(otp)){
            oneTimePasswordRepository.delete(oneTimePassword);
            return new PasswordResetTokenDTO(jwtUtil.generateToken(email));
        }
        throw new InvalidOtpException("Invalid OTP");
    }
}
