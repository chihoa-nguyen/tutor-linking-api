package com.nchowf.tutorlinking.utils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @NotNull(message = "Số nhà không được thiếu")
    @NotEmpty(message = "Số nhà không được thiếu")
    private String streetNumber;

    @NotNull(message = "Phường/xã không được thiếu")
    @NotEmpty(message = "Phường/xã không được thiếu")
    private String ward;

    @NotNull(message = "Quận/huyện không được thiếu")
    @NotEmpty(message = "Quận/huyện không được thiếu")
    private String district;

    @NotNull(message = "Tỉnh thành không được thiếu")
    @NotEmpty(message = "Tỉnh thành không được thiếu")
    private String city;
}
