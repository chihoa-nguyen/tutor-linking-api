package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.parent.dto.ParentUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParentMapper {
    @Mapping(target = "password", ignore = true)
    Parent toParent(ParentRequest parentRequest);
    ParentResponse toParentResponse(Parent parent);
    @Mapping(target = "id", ignore = true)
    void toUpdateParent(@MappingTarget Parent parent, ParentUpdateRequest request);
}
