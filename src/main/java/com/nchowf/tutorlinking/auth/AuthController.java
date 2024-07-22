package com.nchowf.tutorlinking.auth;

import com.nchowf.tutorlinking.token.RefreshTokenRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/{role}/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> parentLogin(@RequestBody @Valid AuthRequest request, @PathVariable String role) throws JOSEException, ParseException {
        return ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công")
                .data(authService.authenticate(request, role))
                .build();
    }
    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .message("Làm mới mã token thành công")
                .data(authService.refreshToken(request))
                .build();
    }
}
