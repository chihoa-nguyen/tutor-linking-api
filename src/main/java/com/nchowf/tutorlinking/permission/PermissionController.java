package com.nchowf.tutorlinking.permission;


import com.nchowf.tutorlinking.permission.dto.PermissionRequest;
import com.nchowf.tutorlinking.permission.dto.PermissionResponse;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping()
    ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.createPermission(request))
                .build();
    }

    @GetMapping()
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermission())
                .build();
    }

    @PutMapping()
    ApiResponse<PermissionResponse> update(@RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.updatePermission(request))
                .build();
    }
    @GetMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name) {
        permissionService.deletePermission(name);
        return ApiResponse.<Void>builder()
                .build();
    }
}
