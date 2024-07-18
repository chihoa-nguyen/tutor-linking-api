package com.nchowf.tutorlinking.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Người dùng chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền này", HttpStatus.FORBIDDEN),
    PASSWORD_WRONG(1016,"Mật khảu không chính xác" , HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_USED(1018,"Số điện thoại đã được sử dụng", HttpStatus.BAD_REQUEST),
    CLASS_NOT_FOUND(1019,"Lớp học không tồn tại" , HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND(1020,"Email này chưa đăng ký/xác thực" , HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND(1021,"Môn học không tồn tại" ,HttpStatus.NOT_FOUND ),
    SUBJECT_EXISTED(1022,"Môn học đã tồn tại" , HttpStatus.BAD_REQUEST),
    GRADE_NOT_FOUND(1023,"Khối học không tồn tại" , HttpStatus.NOT_FOUND ),
    GRADE_EXISTED(1024,"Khối học đã tồn tại" ,HttpStatus.BAD_REQUEST ),
    NOT_YOUR_CLASS(1025,"Lớp học này không phải của bạn" , HttpStatus.BAD_REQUEST),
    ALREADY_REGISTERED(1026,"Bạn đã đăng ký nhận lớp này" , HttpStatus.BAD_REQUEST),
    ENROLLMENT_NOT_FOUND(1027,"Đăng ký nhận lớp không tồn tại" ,HttpStatus.NOT_FOUND ),
    NOT_YOUR_ENROLLMENT(1028,"Bạn không thể xóa đăng ký của gia sư khác" , HttpStatus.FORBIDDEN);
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    @Getter
    private final int code;
    @Getter
    private final String message;
    private final HttpStatusCode statusCode;
}
