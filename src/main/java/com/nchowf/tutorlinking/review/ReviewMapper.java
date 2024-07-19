package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.review.dto.ReviewRequest;
import com.nchowf.tutorlinking.review.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "enrollment", ignore = true)
    Review toReview (ReviewRequest request);
    @Mapping(target = "tutor", ignore = true)
    @Mapping(target = "classroom", ignore = true)
    ReviewResponse toReviewResponse(Review review);
}
