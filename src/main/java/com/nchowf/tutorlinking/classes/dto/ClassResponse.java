package com.nchowf.tutorlinking.classes.dto;

import com.nchowf.tutorlinking.parent.dto.ParentResponse;
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
public class ClassResponse {
    private Integer id;
    private ParentResponse parent;
    private Set<String> subjects;
    private Integer numberSession;
    private String time;
    private String grade;
    private int fee;
    private Address address;
    private String positionRequired;
    private String genderRequired;
    private String note;
    private Date createdAt;
    private Date updatedAt;
}
