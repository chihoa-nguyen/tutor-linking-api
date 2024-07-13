package com.nchowf.tutorlinking.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email này đã được sử dụng", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Người dùng chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn không có quyền này", HttpStatus.FORBIDDEN),
    PASSWORD_WRONG(1016,"Mật khảu không chính xác" , HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_USED(1018,"Số điện thoại này đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    CLASS_NOT_FOUND(1019,"Lớp học không tồn tại trong hệ thống" , HttpStatus.NOT_FOUND),
    EMAIL_NOT_FOUND(1020,"Email chưa được xác thực hoặc không tồn tại" , HttpStatus.NOT_FOUND);

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
