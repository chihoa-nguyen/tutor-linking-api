package com.nchowf.tutorlinking.classes.dto;

import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import com.nchowf.tutorlinking.utils.Address;
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
public class ClassDetailResponse {
    private Integer id;
    private ParentResponse parent;
    private String subjects;
    private Integer numberSession;
    private String time;
    private String grade;
    private int fee;
    private String address;
    private String positionRequired;
    private String genderRequired;
    private String note;
    private Date createdAt;
    private Date updatedAt;
}
