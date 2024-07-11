package com.nchowf.tutorlinking.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotNull(message = "Họ tên không được thiếu")
    @NotBlank(message = "Họ tên không được để trống")
    private String name;
    @NotNull(message = "Số điện thoại không được thiếu")
    @Size(min=10, max=10, message = "Số điện thoại phải có đủ 10 chữ số")
    @Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
}
