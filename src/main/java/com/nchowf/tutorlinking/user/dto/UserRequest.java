package com.nchowf.tutorlinking.user.dto;

import com.nchowf.tutorlinking.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "Họ tên không được thiếu")
    @NotBlank(message = "Họ tên không được để trống")
    private String name;
    @Email(message = "Email không hợp lệ", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull(message = "Số điện thoại không được thiếu")
    @Size(min=10, max=10, message = "Số điện thoại phải có đủ 10 chữ số")
    @Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    @NotNull(message = "Mật khẩu không được thiếu")
    @Size(min=6, max=15, message = "Độ dài mật khẩu từ 6 đến 15 kí tự")
    private String password;
}
