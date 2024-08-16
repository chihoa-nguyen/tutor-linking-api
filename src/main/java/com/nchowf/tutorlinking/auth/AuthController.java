package com.nchowf.tutorlinking.auth;

import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.token.RefreshTokenRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/{role}/login")
    public ApiResponse<AuthResponse> login(@RequestBody @Valid AuthRequest request, @PathVariable String role) throws JOSEException, ParseException {
        return ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công")
                .data(authService.authenticate(request, role))
                .build();
    }
    @PostMapping("/{role}/{id}")
    public ApiResponse<Void> sendVerificationEmail(@PathVariable String role, @PathVariable Integer id){
        return ApiResponse.<Void>builder()
                .message("Gửi mail xác thực thành công")
                .data(authService.sendEmail(role,id))
                .build();
    }
    @PostMapping("/parent/register")
    public ApiResponse<ParentResponse> register(@RequestBody @Valid ParentRequest request) {
        return ApiResponse.<ParentResponse>builder()
               .message("Tạo mới phụ huynh thành công")
               .data(authService.parentRegister(request))
               .build();
    }
    @PostMapping("/tutor/register")

    public ApiResponse<TutorDetailResponse> register(@RequestBody @Valid TutorRequest request) {
        return ApiResponse.<TutorDetailResponse>builder()
                .message("Tạo mới gia sư thành công")
                .data(authService.tutorRegister(request))
                .build();
    }
    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .message("Làm mới mã token thành công")
                .data(authService.refreshToken(request))
                .build();
    }
    @PostMapping("/{role}/change-password")
    public ApiResponse<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request, @PathVariable String role){
        return ApiResponse.<Void>builder()
               .message("Thay đổi mật khẩu thành công")
                .data(authService.changePassword(request, role))
               .build();
    }
}
