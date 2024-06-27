package com.nchowf.tutorlinking.role;

import com.nchowf.tutorlinking.role.dto.RoleRequest;
import com.nchowf.tutorlinking.role.dto.RoleResponse;
import com.nchowf.tutorlinking.role.dto.RoleUpdateRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    ApiResponse<RoleResponse> create(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAllRole())
                .build();
    }

    @GetMapping("/{name}")
    ApiResponse<RoleResponse> getById(@PathVariable String name) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.getRole(name))
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<RoleResponse> update(@PathVariable String name, @RequestBody @Valid RoleUpdateRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.updateRole(name, request))
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name) {
        roleService.deleteRole(name);
        return ApiResponse.<Void>builder().build();
    }
}
