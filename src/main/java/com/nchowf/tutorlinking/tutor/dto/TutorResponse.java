package com.nchowf.tutorlinking.tutor.dto;

import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorResponse extends UserResponse {
    private String birthday;
    private String gender;
    private String avt;
    private String degree;
    private String address;
    private String university_name;
    private String major;
    private String position;
    private Set<SubjectResponse> subjects;
    private Set<GradeResponse> grades;
    private Set<String> teachingArea;
    private String desc;
}
