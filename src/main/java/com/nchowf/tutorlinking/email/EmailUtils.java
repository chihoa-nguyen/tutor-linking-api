package com.nchowf.tutorlinking.email;

import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.enrollment.Enrollment;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.utils.Address;
import org.springframework.beans.factory.annotation.Value;

public class EmailUtils {
    @Value("${spring.mail.verify.host}")
    private static String host;
    public static String getVerificationMessage(String name, String userType, String token) {
        return "Xin chào " + name + ",\n\nBạn đâ đăng ký tài khoản trên TutorLinking."
                + "\nTrước khi có thể sử dụng tài khoản của mình, bạn cần xác minh rằng đây là địa chỉ email của mình bằng cách nhấp vào đây:\n"
                + getVerificationUrl(userType, token)
                + "\n\nXin cảm ơn,"
                + "\nTutorLinking";
    }
    public static String getPasswordResetMessage(String userType, String token) {
        return "Xin chào, \n\n"
                + "Bạn đã yêu cầu khôi phục mật khẩu cho tài khoản của mình trên TutorLinking."
                + "\nĐể đặt lại mật khẩu, vui lòng nhấp vào đây:\n"
                + getPasswordResetUrl(userType, token)
                + "\n\nXin cảm ơn,"
                + "\nTutorLinking";
    }

    private static String getPasswordResetUrl(String userType, String token) {
        return host + userType + "/reset-password?token=" + token;
    }

    private static String getVerificationUrl(String userType, String token) {
        return host + userType + "/verify?token=" + token;
    }
    private static String getClassInfoUrl(Integer id){
        return "http://localhost:5173/classes/" + id;
    }
    public static String getClassDetails(Enrollment enrollment) {
        StringBuilder subjects = new StringBuilder();
        for (Subject subject : enrollment.getClassroom().getSubjects()) {
            subjects.append(subject.getName()).append(", ");
        }
        subjects.deleteCharAt(subjects.lastIndexOf(", "));
        Address address = enrollment.getClassroom().getAddress();
        String fullAddress = java.lang.String.format("%s, %s, %s, %s", address.getStreetNumber(), address.getWard(), address.getDistrict(), address.getCity());
        return "Kính gửi gia sư " + enrollment.getTutor().getName() + ","
                + "\nPhụ huynh đã chấp nhận đăng ký nhận lớp của bạn."
                + "\n\nThông tin chi tiết lớp dạy:"
                + "\n- Mã lớp: " + enrollment.getClassroom().getId()
                + "\n- Môn học: " + subjects
                + "\n- Khối học: " + enrollment.getClassroom().getGrade().getName()
                + "\n- Số buổi học: " + enrollment.getClassroom().getNumberSession()
                + "\n- Thời gian học: " + enrollment.getClassroom().getTime()
                + "\n- Mức học phí: " + enrollment.getClassroom().getFee() + " đồng/buổi"
                + "\n- Yêu cầu chức vụ: " + enrollment.getClassroom().getPositionRequired().value()
                + "\n- Yêu cầu giới tính: " + enrollment.getClassroom().getGenderRequired().value()
                + "\n- Địa điểm học: " + fullAddress
                + "\n\nThông tin liên hệ phụ huynh:"
                + "\n- Họ và tên: " + enrollment.getClassroom().getParent().getName()
                + "\n- Số điện thoại: " + enrollment.getClassroom().getParent().getPhoneNumber()
                + "\n- Email: " + enrollment.getClassroom().getParent().getEmail()
                + "\nLưu ý: Vui lòng liên hệ trực tiếp với phụ huynh để xác nhận thời gian học,"
                + "\nđịa điểm học và mức học phí trước khi nhận lớp."
                + "\n\nTrân trọng,"
                + "\nTutorLinking";
    }
    public static String classSuitableInfo(ClassResponse classroom, String tutorName){
        return "Kính gửi gia sư " + tutorName + ","
                + "\nHệ thống có lớp mới phù hợp với hồ sơ của gia sư."
                + "\n\nThông tin lớp học:"
                + "\n- Mã lớp: " + classroom.getId()
                + "\n- Môn học: " + classroom.getSubjects()
                + "\n- Khối học: " + classroom.getGrade()
                + "\n- Số buổi học: " + classroom.getNumberSession()
                + "\n- Thời gian học: " + classroom.getTime()
                + "\n- Mức học phí: " + classroom.getFee() + " đồng/buổi"
                + "\n- Yêu cầu chức vụ: " + classroom.getPositionRequired()
                + "\n- Yêu cầu giới tính: " + classroom.getGenderRequired()
                + "\n- Địa điểm học: " + classroom.getAddress()
                + "\nĐăng kí nhận lớp tại: " + getClassInfoUrl(classroom.getId())
                + "\n\nTrân trọng,"
                + "\nTutorLinking";
    }
}