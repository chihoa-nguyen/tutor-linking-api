package com.nchowf.tutorlinking.email;

import com.nchowf.tutorlinking.enrollment.Enrollment;
import com.nchowf.tutorlinking.subject.Subject;

public class EmailUtils {
    public static String getVerificationMessage(String name, String userType, String host, String token) {
        return "Xin chào " + name + ",\n\nBạn đâ đăng ký tài khoản trên TutorLinking."
                + "\nTrước khi có thể sử dụng tài khoản của mình, bạn cần xác minh rằng đây là địa chỉ email của mình bằng cách nhấp vào đây:\n"
                + getVerificationUrl(host, userType, token)
                + "\n\nXin cảm ơn,"
                + "\nTutorLinking";
    }

    public static String getVerificationUrl(String host, String userType, String token) {
        return host + userType + "/verify?token=" + token;
    }

    public static String getClassDetails(Enrollment enrollment) {
        StringBuilder subjects = new StringBuilder();
        for (Subject subject : enrollment.getClassroom().getSubjects()) {
            subjects.append(subject.getName()).append(", ");
        }
        subjects.deleteCharAt(subjects.lastIndexOf(", "));
        return "Kính gửi gia sư " + enrollment.getTutor().getName() + ","
                + "\nPhụ huynh đã chấp nhận đăng ký nhận lớp " + enrollment.getClassroom().getId() + " của bạn."
                + "\n\nThông tin chi tiết lớp dạy:"
                + "\n- Mã lớp: " + enrollment.getClassroom().getId()
                + "\n- Môn học: " + subjects
                + "\n- Khối học: " + enrollment.getClassroom().getGrade().getName()
                + "\n- Số buổi học: " + enrollment.getClassroom().getNumberSession()
                + "\n- Thời gian học: " + enrollment.getClassroom().getTime()
                + "\n- Mức học phí: " + enrollment.getClassroom().getFee() + " đồng/buổi"
                + "\n- Yêu cầu chức vụ: " + enrollment.getClassroom().getPositionRequired().value()
                + "\n- Yêu cầu giới tính: " + enrollment.getClassroom().getGenderRequired().value()
                + "\n- Địa điểm học: " + enrollment.getClassroom().getAddress()
                + "\n\nThông tin liên hệ phụ huynh:"
                + "\n- Họ và tên: " + enrollment.getClassroom().getParent().getName()
                + "\n- Số điện thoại: " + enrollment.getClassroom().getParent().getPhoneNumber()
                + "\n- Email: " + enrollment.getClassroom().getParent().getEmail()
                + "\nLưu ý: Vui lòng liên hệ trực tiếp với phụ huynh để xác nhận thời gian học,"
                + "\nđịa điểm học và mức học phí trước khi nhận lớp."
                + "\n\nTrân trọng,"
                + "\nTutorLinking";
    }
}