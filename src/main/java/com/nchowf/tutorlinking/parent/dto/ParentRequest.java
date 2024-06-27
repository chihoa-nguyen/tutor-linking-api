package com.nchowf.tutorlinking.parent.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentRequest {
    @NotNull(message = "Họ tên không được thiếu")
    @NotBlank(message = "Họ tên không được để trống")
    private String name;
    @Min(value = 0, message = "Chỉ nhận 0 (Nam) hoặc 1 (Nữ)")
    @Max(value = 0, message = "Chỉ nhận 0 (Nam) hoặc 1 (Nữ)")
    private int gender;
    @NotNull(message = "Số điện thoại không được thiếu")
    @Size(min=10, max=10, message = "Số điện thoại phải có đủ 10 chữ số")
    @Pattern(regexp = "^[0-9\\-+]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
    @Email(message = "Email không hợp lệ", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private String address;
    @NotNull(message = "Mật khẩu không được thiếu")
    @Size(min=6, max=15, message = "Độ dài mật khẩu từ 6 đến 15 kí tự")
    private String password;
}
