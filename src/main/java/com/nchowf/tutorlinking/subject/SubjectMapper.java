package com.nchowf.tutorlinking.subject;

import com.nchowf.tutorlinking.subject.dto.SubjectRequest;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    Subject toSubject (SubjectRequest subjectRequest);
    SubjectResponse toSubjectResponse (Subject subject);
}
