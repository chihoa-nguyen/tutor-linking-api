package com.nchowf.tutorlinking.classes.dto;

import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Integer id;
    private Set<SubjectResponse> subjects;
    private Integer numberSession;
    private String time;
    private GradeResponse grade;
    private int fee;
    private String address;
    private String positionRequired;
    private String genderRequired;
    private String note;
    private Date createdAt;
}
