package com.nchowf.tutorlinking.exception;

import com.nchowf.tutorlinking.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingRuntimeException(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<String> handlingIllegalArgumentException(Exception exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(SQLException.class)
    ResponseEntity<String> handleSqlException(SQLException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<ApiResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(9999);
        apiResponse.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

//    @ExceptionHandler(GoogleJsonResponseException.class)
//    ResponseEntity<String> handleGoogleJsonResponseException(GoogleJsonResponseException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }

    @ExceptionHandler(IOException.class)
    ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
