package com.autoleasing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    private JavaMailSender javaMailSender;
    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(adminEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(topic);
        javaMailSender.send(simpleMailMessage);
    }

    public String getAdminEmail() {
        return adminEmail;
    }
}
