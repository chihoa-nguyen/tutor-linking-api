package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.utils.enums.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel ="spring")
public interface ParentMapper {
    @Mapping(target = "gender", source = "gender", qualifiedByName ="intToGender" )
    Parent toParent(ParentRequest parentRequest);
    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    ParentResponse toParentResponse(Parent parent);
    @Named("genderToString")
    static String genderToString(Gender gender) {
        return gender != null ? gender.value() : null;
    }
    @Named("intToGender")
    static Gender intToGender(int gender) {
        return gender == 0? Gender.MAN : Gender.FEMALE;
    }
    @Mapping(target ="parentId", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName ="intToGender" )
    void toUpdateParent(@MappingTarget Parent parent, ParentRequest parentRequest);
}
