package com.nchowf.tutorlinking.role;

import com.nchowf.tutorlinking.permission.PermissionMapper;
import com.nchowf.tutorlinking.role.dto.RoleRequest;
import com.nchowf.tutorlinking.role.dto.RoleResponse;
import com.nchowf.tutorlinking.role.dto.RoleUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    @Mapping(source = "permissions", target = "permissions")
    RoleResponse toRoleResponse(Role role);
    @Mapping(target = "permissions", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}
