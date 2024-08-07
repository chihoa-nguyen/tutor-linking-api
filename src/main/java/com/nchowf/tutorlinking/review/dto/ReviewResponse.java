package com.nchowf.tutorlinking.review.dto;

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
    private String parentName;
    private int rating;
    private String comment;
}
