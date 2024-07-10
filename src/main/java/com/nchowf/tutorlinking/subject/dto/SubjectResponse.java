package com.nchowf.tutorlinking.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {
    private Long id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
