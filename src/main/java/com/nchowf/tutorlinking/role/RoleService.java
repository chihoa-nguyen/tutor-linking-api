package com.nchowf.tutorlinking.role;

import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.permission.Permission;
import com.nchowf.tutorlinking.permission.PermissionRepository;
import com.nchowf.tutorlinking.role.dto.RoleRequest;
import com.nchowf.tutorlinking.role.dto.RoleResponse;
import com.nchowf.tutorlinking.role.dto.RoleUpdateRequest;
import com.nchowf.tutorlinking.utils.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsById(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public RoleResponse getRole(String name) {
        return roleMapper.toRoleResponse(roleRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
    }

    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse updateRole(String roleName, RoleUpdateRequest request) {
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        roleMapper.updateRole(role, request);
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
