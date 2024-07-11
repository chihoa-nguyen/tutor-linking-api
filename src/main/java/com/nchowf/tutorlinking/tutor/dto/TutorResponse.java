package com.nchowf.tutorlinking.tutor.dto;

import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TutorResponse extends UserResponse {
    private String birthday;
    private String gender;
    private String avt;
    private String degree;
    private String universityName;
    private String major;
    private String position;
    private Set<SubjectResponse> subjects;
    private Set<GradeResponse> grades;
    private Set<String> teachingArea;
    private String description;
}
