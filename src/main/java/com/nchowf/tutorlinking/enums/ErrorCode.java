package com.nchowf.tutorlinking.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    EMAIL_EXISTED("Email đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED( "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Người dùng chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("Bạn không có quyền này", HttpStatus.FORBIDDEN),
    PASSWORD_WRONG("Mật khảu không chính xác" , HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_USED("Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST),
    CLASS_NOT_FOUND("Lớp học không tồn tại" , HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND("Email này chưa đăng ký/xác thực" , HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND("Môn học không tồn tại" ,HttpStatus.NOT_FOUND ),
    SUBJECT_EXISTED("Môn học đã tồn tại" , HttpStatus.BAD_REQUEST),
    GRADE_NOT_FOUND("Khối học không tồn tại" , HttpStatus.NOT_FOUND ),
    GRADE_EXISTED("Khối học đã tồn tại" ,HttpStatus.BAD_REQUEST ),
    NOT_YOUR_CLASS("Lớp học này không phải của bạn" , HttpStatus.BAD_REQUEST),
    ALREADY_REGISTERED("Bạn đã đăng ký nhận lớp này" , HttpStatus.BAD_REQUEST),
    ENROLLMENT_NOT_FOUND("Đăng ký nhận lớp không tồn tại" ,HttpStatus.NOT_FOUND ),
    NOT_YOUR_ENROLLMENT("Bạn không thể xóa đăng ký của gia sư khác" , HttpStatus.FORBIDDEN),
    INVALID_ENROLLMENT("Đăng ký nhận lớp này chưa được giao nên không thể đánh giá gia sư" , HttpStatus.BAD_REQUEST),
    ALREADY_REVIEWED("Mỗi lớp chỉ được đánh giá một lần" , HttpStatus.BAD_REQUEST),
    USER_NOT_ENABLED("Tài khoản chưa được kích hoạt", HttpStatus.BAD_REQUEST ),
    MAX_FILE_SIZE("Kích thước file không quá 2MB", HttpStatus.BAD_REQUEST),
    NOT_ALLOWED_FILE("Chỉ được dùng file có đuôi jpg, png, gif, bmp",HttpStatus.BAD_REQUEST ),
    CLASS_CANNOT_DELETE("Bạn không thể xóa lớp đã có gia sư",HttpStatus.BAD_REQUEST);
    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    @Getter
    private final String message;
    private final HttpStatusCode statusCode;
}
