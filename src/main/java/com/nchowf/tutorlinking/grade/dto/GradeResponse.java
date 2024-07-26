package com.nchowf.tutorlinking.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeResponse {
    private Integer id;
    private String name;
//    private Date createdAt;
//    private Date updatedAt;
}
