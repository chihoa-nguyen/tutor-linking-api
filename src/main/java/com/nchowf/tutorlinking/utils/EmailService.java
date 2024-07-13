package com.nchowf.tutorlinking.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    @Async
    public void send(String name, String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Xác thực email");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(to);
        message.setText("Nhấn vào link đi kèm đẻ xác thực tài khoản!");
        emailSender.send(message);
    }
}
