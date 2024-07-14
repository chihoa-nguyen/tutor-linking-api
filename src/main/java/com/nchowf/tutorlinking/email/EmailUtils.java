package com.nchowf.tutorlinking.email;

public class EmailUtils {
    public static String getEmailMessage(String name, String userType, String host, String token) {
        return "Xin chào " + name + ",\n\nBạn đâ đăng ký tài khoản trên TutorLinking." + "\nTrước khi có thể sử dụng tài khoản của mình, bạn cần xác minh rằng đây là địa chỉ email của mình bằng cách nhấp vào đây:\n" +
                getVerificationUrl(host, userType, token) + "\n\nXin cảm ơn," +"\nTutorLinking";

    }
    public static String getVerificationUrl(String host, String userType, String token) {
        return host + userType +"/verify?token=" + token;
    }
}
