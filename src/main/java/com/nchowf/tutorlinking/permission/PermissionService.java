package com.nchowf.tutorlinking.permission;


import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.utils.enums.ErrorCode;
import com.nchowf.tutorlinking.permission.dto.PermissionRequest;
import com.nchowf.tutorlinking.permission.dto.PermissionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse).toList();
    }
    public PermissionResponse updatePermission(PermissionRequest request) {
        Permission permission = permissionRepository.findById(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.updatePermission(permission, request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }
    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
