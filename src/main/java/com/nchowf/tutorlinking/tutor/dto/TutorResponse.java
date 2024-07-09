package com.nchowf.tutorlinking.tutor.dto;

import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
public class TutorResponse extends UserResponse {
    private String birthday;
    private String gender;
    private String avt;
    private String degree;
    private String university_name;
    private String major;
    private String position;
    private Set<SubjectResponse> subjects;
    private Set<GradeResponse> grades;
    private Set<String> teachingArea;
    private String desc;
    public TutorResponse() {
        super();
    }
    public TutorResponse(Integer id, String email, String name, String phoneNumber, String address, String birthday, String gender, String avt, String degree, String university_name, String major, String position, Set<SubjectResponse> subjects, Set<GradeResponse> grades, Set<String> teachingArea, String desc) {
        super(id, email, name, phoneNumber, address);
        this.birthday = birthday;
        this.gender = gender;
        this.avt = avt;
        this.degree = degree;
        this.university_name = university_name;
        this.major = major;
        this.position = position;
        this.subjects = subjects;
        this.grades = grades;
        this.teachingArea = teachingArea;
        this.desc = desc;
    }

}
