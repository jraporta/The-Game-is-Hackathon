package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.VerifyOtpDTO;
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

    private OneTimePasswordRepository oneTimePasswordRepository;
    private UserRepository userRepository;
    private MailSender mailSender;
    private JwtUtil jwtUtil;

    public PasswordResetService(OneTimePasswordRepository oneTimePasswordRepository, UserRepository userRepository,
                                MailSender mailSender, JwtUtil jwtUtil) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.jwtUtil = jwtUtil;
    }

    public void resetPassword(String email) {
        if (!userRepository.existsByEmail(email)) throw new NonExistingIdentifierException("");
        OneTimePassword oldOtp = oneTimePasswordRepository.findByEmail(email);
        if (oldOtp != null ) oneTimePasswordRepository.delete(oldOtp);
        OneTimePassword otp = new OneTimePassword();
        otp.setEmail(email);
        otp.setOtp(Integer.toString((int) (Math.random() * 100000)));
        oneTimePasswordRepository.save(otp);
        sendMail(email, "Password Reset", "OTP:" + otp.getOtp());
    }

    public void sendMail(String to, String subject, String text){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        mailSender.send(email);
    }

    public String verifyOTP(VerifyOtpDTO verifyOtpDTO) {
        OneTimePassword oneTimePassword = oneTimePasswordRepository.findByEmail(verifyOtpDTO.getIdentifier());
        if (oneTimePassword != null && oneTimePassword.getOtp().equals(verifyOtpDTO.getOtp())){
            oneTimePasswordRepository.delete(oneTimePassword);
            return jwtUtil.generateToken(verifyOtpDTO.getIdentifier());
        }
        throw new InvalidOtpException("Invalid OTP");
    }
}
