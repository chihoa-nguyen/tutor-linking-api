package com.nchowf.tutorlinking.tutor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorResponse {
    private Integer id;
    private String name;
    private String birthday;
    private String gender;
    private String avt;
    private String position;
    private String universityName;
    private String major;
    private Set<String> subjects;
    private Set<String> grades;
    private Set<String> teachingArea;
    private String description;
}
