package com.nchowf.tutorlinking.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Async
    public void sendVerificationMail(String name, String to, String token, String userType){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Xác thực email cho tài khoản TutorLinking");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(to);
        message.setText(EmailUtils.getEmailMessage(name, userType ,host, token));
        emailSender.send(message);
    }
}
