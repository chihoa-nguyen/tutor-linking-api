package com.nchowf.tutorlinking.auth.dto;

import com.nchowf.tutorlinking.auth.dto.ResetPasswordRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest extends ResetPasswordRequest {
    @NotEmpty(message = "Email không được thiếu")
    private String email;
    @NotEmpty(message = "Mật khẩu cũ không được thiếu")
    private String oldPassword;
}
