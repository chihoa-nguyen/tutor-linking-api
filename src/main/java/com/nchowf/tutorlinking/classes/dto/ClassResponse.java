package com.nchowf.tutorlinking.classes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Integer id;
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
}
