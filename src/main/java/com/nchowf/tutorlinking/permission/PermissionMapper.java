package com.nchowf.tutorlinking.permission;


import com.nchowf.tutorlinking.permission.dto.PermissionRequest;
import com.nchowf.tutorlinking.permission.dto.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    @Mapping(target="name", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
