package com.sergio.bookstore.service.mails.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String username) {
        log.info("Sending welcome email to user: {}", username);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username + "@bookstore.com");
        message.setSubject("Welcome to Bookstore!");
        message.setText("Hello " + username + ",\n\nWelcome to our bookstore! Your account has been created successfully.\n\nBest regards,\nBookstore Team");
        message.setFrom("noreply@bookstore.com");
        
        mailSender.send(message);
        log.info("Welcome email sent successfully to: {}", username);
    }

    public void sendPasswordResetEmail(String username) {
        log.info("Sending password reset email to user: {}", username);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username + "@bookstore.com");
        message.setSubject("Password Reset - Bookstore");
        message.setText("Hello " + username + ",\n\nYou have requested a password reset.\n\nBest regards,\nBookstore Team");
        message.setFrom("noreply@bookstore.com");
        
        mailSender.send(message);
        log.info("Password reset email sent successfully to: {}", username);
    }
}

