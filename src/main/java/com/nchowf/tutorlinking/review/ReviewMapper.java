package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.enrollment.Enrollment;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.review.dto.ReviewRequest;
import com.nchowf.tutorlinking.review.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Named("getParentName")
    static String getParentName(Enrollment enrollment) {
        return enrollment.getClassroom().getParent().getName();
    }
    @Mapping(target = "enrollment", ignore = true)
    Review toReview (ReviewRequest request);
    @Mapping(target = "parentName", source = "enrollment", qualifiedByName = "getParentName")
    ReviewResponse toReviewResponse(Review review);
}
