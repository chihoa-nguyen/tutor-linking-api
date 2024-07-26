package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.utils.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    @Named("intToGender")
    static Gender intToGender(int genderRequired) {
        return switch (genderRequired) {
            case 0 -> Gender.MALE;
            case 1 -> Gender.FEMALE;
            default -> Gender.NONE;
        };
    }
    @Named("intToPosition")
    static Position intToPosition(int positionRequired) {
        return switch (positionRequired) {
            case 0 -> Position.STUDENT;
            case 1 -> Position.TEACHER;
            default -> Position.GRADUATED_STUDENT;
        };
    }
    @Named("genderToString")
    static String genderToString(Gender gender) {
        return gender != null ? gender.value() : null;
    }
    @Named("positionToString")
    static String positionToString(Position position) {
        return position != null ? position.value() : null;
    }
//    @Named("subjectsToString")
//    static Set<String> subjectsToString(Set<Subject> subjects){
//        return subjects.stream().map(Subject::getName)
//                .collect(Collectors.toSet());
//    }
    @Named("addressToString")
    static String addressToString(Address address){
        String fullAddress = String.format("%s, %s, %s, %s", address.getStreetNumber(), address.getWard(), address.getWard(), address.getCity());
        int firstSpaceIndex = fullAddress.indexOf(" ");
        if (firstSpaceIndex != -1) {
            return fullAddress.substring(firstSpaceIndex + 1);
        } else {
            return fullAddress;
        }
    }
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "intToGender")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "intToPosition")
    Class toClass(ClassRequest request);
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "genderToString")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "positionToString")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressToString")
    ClassResponse toClassResponse(Class classroom);
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "genderToString")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "positionToString")
    ClassDetailResponse toClassDetailResponse(Class classroom);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "intToGender")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "intToPosition")
    void updateClass(@MappingTarget Class classroom, ClassRequest request);
}
