package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.ClassMapper;
import com.nchowf.tutorlinking.enums.Status;
import com.nchowf.tutorlinking.tutor.TutorMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {TutorMapper.class, ClassMapper.class})
public interface EnrollmentMapper {
    @Named("statusToString")
    static String statusToString(Status status) {
        return status != null ? status.value() : null;
    }
    @Mapping(target ="status", source = "status", qualifiedByName = "statusToString")
    EnrollmentResponse toEnrollmentResponse(Enrollment enrollment);
}
