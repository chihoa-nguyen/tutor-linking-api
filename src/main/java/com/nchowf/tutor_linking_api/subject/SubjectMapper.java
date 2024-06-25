package com.nchowf.tutor_linking_api.subject;

import com.nchowf.tutor_linking_api.subject.dto.SubjectRequest;
import com.nchowf.tutor_linking_api.subject.dto.SubjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toSubject (SubjectRequest subjectRequest);
    SubjectResponse toSubjectResponse (Subject subject);
}
