package com.nchowf.tutorlinking.review.dto;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private Integer id;
    private Date createdAt;
    private Date updatedAt;
    private TutorResponse tutor;
    private ClassDetailResponse classroom;
    private int rating;
    private String comment;
}
