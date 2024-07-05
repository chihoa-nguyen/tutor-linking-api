package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    Parent toParent(ParentRequest parentRequest);
    ParentResponse toParentResponse(Parent parent);
    @Mapping(target = "parentId", ignore = true)
    void toUpdateParent(@MappingTarget Parent parent, ParentRequest parentRequest);
}
