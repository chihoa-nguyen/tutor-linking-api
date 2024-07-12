package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    @Named("intToGender")
    static Gender intToGender(int genderRequired) {
        return genderRequired == 0 ? Gender.MALE :
                (genderRequired == 1 ? Gender.FEMALE : Gender.NONE);
    }
    @Named("intToPosition")
    static Position intToPosition(int positionRequired) {
        return positionRequired == 0 ? Position.STUDENT :
                (positionRequired == 1 ? Position.TEACHER : Position.GRADUATED_STUDENT);
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
    static Set<String> subjectsToString(Set<Subject> subjects){
        Set<String> subjectNames = new HashSet<>();
        subjects.forEach(subject -> subjectNames.add(subject.getName()));
        return subjectNames;
    }
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "intToGender")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "intToPosition")
    Class toClass(ClassRequest request);
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "genderToString")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "positionToString")
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectsToString")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "grade", source = "grade.name")
    ClassResponse toClassResponse(Class classroom);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "genderRequired", source = "genderRequired", qualifiedByName = "intToGender")
    @Mapping(target = "positionRequired", source = "positionRequired", qualifiedByName = "intToPosition")
    void updateClass(@MappingTarget Class classroom, ClassRequest request);
}
