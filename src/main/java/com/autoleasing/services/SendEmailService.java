package com.autoleasing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;

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

    public void sendEmailWithAttachment(String body, String topic, String attachment) throws MessagingException, FileNotFoundException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(adminEmail);
        messageHelper.setTo(adminEmail);
        messageHelper.setSubject(topic);
        messageHelper.setText(body);
        FileSystemResource file = new FileSystemResource(attachment);
        messageHelper.addAttachment("file", file);
        javaMailSender.send(mimeMessage);
    }

    public String getAdminEmail() {
        return adminEmail;
    }
}
