package com.example.hoiiday.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
}
