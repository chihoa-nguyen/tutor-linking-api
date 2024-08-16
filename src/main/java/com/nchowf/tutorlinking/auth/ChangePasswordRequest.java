package com.nchowf.tutorlinking.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    @NotEmpty(message = "Email không được thiếu")
    private String email;
    @NotEmpty(message = "Mật khẩu cũ không được thiếu")
    private String oldPassword;
    @NotEmpty(message = "Mật khẩu mỡi không được thiếu")
    @Size(min = 8, message = "Mật khẩu mới phải có ít nhất 8 ký tự")
    private String newPassword;
    @NotEmpty(message = "Xác nhận mật khẩu mới không được thiếu")
    @Size(min = 8, message = "Xác nhận mật khẩu mới phải có ít nhất 8 ký tự")
    private String confirmNewPassword;
}
