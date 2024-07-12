package com.nchowf.tutorlinking.exception;

import com.nchowf.tutorlinking.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException{
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    private ErrorCode errorCode;

}
