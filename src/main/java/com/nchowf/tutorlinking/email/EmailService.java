package com.nchowf.tutorlinking.email;

import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.enrollment.Enrollment;
import com.nchowf.tutorlinking.tutor.Tutor;
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

    @Async
    public void sendVerificationMail(String name, String to, String token, String role){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Xác thực email cho tài khoản TutorLinking");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(to);
        message.setText(EmailUtils.getVerificationMessage(name, role, token));
        emailSender.send(message);
    }
    public void sendPasswordResetMail(String to, String token, String role){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Reset mật khẩu cho tài khoản TutorLinking");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(to);
        message.setText(EmailUtils.getPasswordResetMessage(role, token));
        emailSender.send(message);
    }
    @Async
    public void sendClassSuitableMail(ClassResponse classroom, Tutor tutor){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Lớp mới phù hợp với bạn");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(tutor.getEmail());
        message.setText(EmailUtils.classSuitableInfo(classroom, tutor.getName()));
        emailSender.send(message);
    }
    @Async
    public void sendClassDetailsMail(Enrollment enrollment, String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Đăng ký nhận lớp thành công");
        message.setFrom("tutor.linking@gmail.com");
        message.setTo(to);
        message.setText(EmailUtils.getClassDetails(enrollment));
        emailSender.send(message);
    }
}
