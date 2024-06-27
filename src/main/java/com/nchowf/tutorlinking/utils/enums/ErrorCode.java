package com.nchowf.tutorlinking.utils.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "Người dùng không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    PERMISSION_EXISTED(1008, "Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1009, "Permission not existed", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED_IN_ROLE(1010, "Permission existed in role", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1011, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1012, "Role not existed", HttpStatus.NOT_FOUND),
    SUBJECT_EXISTED(1013,"Môn học đã tồn tại !",HttpStatus.BAD_REQUEST ),
    SUBJECT_NOT_FOUND(1014,"Môn học không tồn tại",HttpStatus.NOT_FOUND ),
    GRADE_EXISTED(1013,"Khối học đã tồn tại !",HttpStatus.BAD_REQUEST ),
    GRADE_NOT_FOUND(1014,"Khối học không tồn tại",HttpStatus.NOT_FOUND ),
    BOOK_NOT_FOUND(1015,"Book not existed",HttpStatus.NOT_FOUND),
    PASSWORD_WRONG(1016,"Invalid password" , HttpStatus.BAD_REQUEST),
    EMAIL_USED(1017,"Email này đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_USED(1018,"Số điện thoại này đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
