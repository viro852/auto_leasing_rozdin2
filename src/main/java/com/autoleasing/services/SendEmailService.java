package com.autoleasing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {


    private JavaMailSender javaMailSender;

    @Autowired
    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom("rozdin92@gmail.com");
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(topic);
        javaMailSender.send(simpleMailMessage);
    }
}
