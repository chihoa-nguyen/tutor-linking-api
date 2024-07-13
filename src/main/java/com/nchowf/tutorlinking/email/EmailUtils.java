package com.nchowf.tutorlinking.email;

public class EmailUtils {
    public static String getEmailMessage(String name, String userType, String host, String token) {
        return "Xin chào " + name + ",\n\nTài khoản Tutor Linking của bạn đã được tạo. Vui lòng nhấp vào liên kết bên dưới để xác minh tài khoản của bạn. \n\n" +
                getVerificationUrl(host, userType, token) + "\n\nVui lòng không trả lời email này";

    }
    public static String getVerificationUrl(String host, String userType, String token) {
        return host + userType +"/verify?token=" + token;
    }
}
