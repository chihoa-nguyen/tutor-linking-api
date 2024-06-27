package com.nchowf.tutorlinking.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RoleUpdateRequest {
    @NotNull(message = "Role name is not null")
    @NotBlank(message = "Role name is required")
    private String description;
    @NotNull
    @Size(min=1, message = "Permission list is required")
    private Set<String> permissions;
}
