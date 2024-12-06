package com.nchowf.tutorlinking.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email must be not null")
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 6, max = 15, message = "Password length must be between 8 and 15 characters")
    private String password;
}
