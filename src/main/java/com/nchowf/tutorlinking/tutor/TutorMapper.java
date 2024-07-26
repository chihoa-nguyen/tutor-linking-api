package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface TutorMapper {
    @Named("intToGender")
    static Gender intToGender(int gender) {
        return gender == 0 ? Gender.MALE : Gender.FEMALE;
    }

    @Named("intToPosition")
    static Position intToPosition(int position) {
        return position == 0 ? Position.STUDENT :
                (position == 1 ? Position.TEACHER : Position.GRADUATED_STUDENT);
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
    @Named("gradesToString")
    static Set<String> gradesToString(Set<Grade> grades){
        Set<String> gradeNames = new HashSet<>();
        grades.forEach(grade -> gradeNames.add(grade.getName()));
        return gradeNames;
    }
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "avt", ignore = true)
    @Mapping(target = "degree", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "intToGender")
    @Mapping(target = "position", source = "position", qualifiedByName = "intToPosition")
    Tutor toTutor(TutorRequest request);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    @Mapping(target = "position", source = "position", qualifiedByName = "positionToString")
    TutorDetailResponse tuTutorDetailResponse(Tutor tutor);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToString")
    @Mapping(target = "position", source = "position", qualifiedByName = "positionToString")
    @Mapping(target = "subjects", source = "subjects", qualifiedByName = "subjectsToString")
    @Mapping(target = "grades", source = "grades", qualifiedByName = "gradesToString")
    TutorResponse tuTutorResponse(Tutor tutor);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "avt", ignore = true)
    @Mapping(target = "degree", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "intToGender")
    @Mapping(target = "position", source = "position", qualifiedByName = "intToPosition")
    void updateTutor(@MappingTarget Tutor tutor, TutorUpdateRequest request);
}
