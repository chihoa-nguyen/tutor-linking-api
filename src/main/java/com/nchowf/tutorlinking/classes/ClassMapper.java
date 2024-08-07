package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.utils.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;

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
    @Named("subjectsToString")
    static String subjectsToString(Set<Subject> subjects){
        StringBuilder result = new StringBuilder();
        for (Subject subject : subjects) {
            result.append(subject.getName()).append(", ");
        }
        result.deleteCharAt(result.lastIndexOf(", "));
        return result.toString();
    }
    @Named("addressToString")
    static String addressToString(Address address){
        String fullAddress = String.format("%s, %s, %s, %s", address.getStreetNumber(), address.getWard(), address.getDistrict(), address.getCity());
        int firstSpaceIndex = fullAddress.indexOf(" ");
        if (firstSpaceIndex != -1) {
            return fullAddress.substring(firstSpaceIndex + 1);
        } else {
            return fullAddress;
        }
    }
    @Named("getFullAddress")
    static String getFullAddress(Address address){
        return String.format("%s, %s, %s, %s", address.getStreetNumber(), address.getWard(), address.getDistrict(), address.getCity());
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
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectsToString")
    @Mapping(target = "grade", source = "grade.name")
    ClassResponse toClassResponse(Class classroom);
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "genderToString")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "positionToString")
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectsToString")
    @Mapping(target = "address", source = "address", qualifiedByName = "getFullAddress")
    @Mapping(target = "grade", source = "grade.name")
    ClassDetailResponse toClassDetailResponse(Class classroom);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "intToGender")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "intToPosition")
    void updateClass(@MappingTarget Class classroom, ClassRequest request);
}
