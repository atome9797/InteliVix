package com.intellivix.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class mailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String text) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(to);
        helper.setTo("intellivix@intellivix.com");
        helper.setSubject("인텔리빅스 문의");
        helper.setText(text);

        emailSender.send(message);


//        SimpleMailMessage message = createMessage(to, subject, text);
//                try{
//                    emailSender.send(message);
//                } catch(MailException es) {
//                    es.printStackTrace();
//                    throw new IllegalArgumentException();
//                }
    }

    private SimpleMailMessage createMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        return message;
    }
}
