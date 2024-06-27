package com.nchowf.tutorlinking.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Long classId;
    private Integer parentId;
    private Set<String> subjects;
    private Integer numberSession;
    private String time;
    private String grade;
    private String address;
    private String positionRequired;
    private String genderRequired;
    private String note;
}
