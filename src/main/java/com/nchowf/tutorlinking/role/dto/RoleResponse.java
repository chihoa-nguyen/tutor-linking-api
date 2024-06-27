package com.nchowf.tutorlinking.role.dto;

import com.nchowf.tutorlinking.permission.dto.PermissionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String name;
    private String description;
    Set<PermissionResponse> permissions;
}
