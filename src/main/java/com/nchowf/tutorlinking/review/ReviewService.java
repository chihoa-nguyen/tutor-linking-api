package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.enrollment.Enrollment;
import com.nchowf.tutorlinking.enrollment.EnrollmentResponse;
import com.nchowf.tutorlinking.enrollment.EnrollmentService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Status;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.review.dto.ReviewRequest;
import com.nchowf.tutorlinking.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final ReviewMapper reviewMapper;
    private final EnrollmentService enrollmentService;
    public ReviewResponse addReview(ReviewRequest request){
        Enrollment enrollment = enrollmentService.findById(request.getEnrollmentId());
        if(enrollment.getStatus() != Status.APPROVED){
            throw new AppException(ErrorCode.INVALID_ENROLLMENT);
        }
        if(reviewRepo.existsByEnrollment(enrollment)){
            throw new AppException(ErrorCode.ALREADY_REVIEWED);
        }
        Review review = reviewMapper.toReview(request);
        review.setEnrollment(enrollment);
        reviewRepo.save(review);
        ReviewResponse reviewResponse = reviewMapper.toReviewResponse(review);
        EnrollmentResponse enrollmentResponse = enrollmentService.getEnrollmentResponse(enrollment);
        reviewResponse.setTutor(enrollmentResponse.getTutor());
        reviewResponse.setClassroom(enrollmentResponse.getClassroom());
        return reviewResponse;
    }
    public List<ReviewResponse> getReviewsByTutorId(Integer tutorId) {
        return reviewRepo.findAllByTutorId(tutorId).stream()
               .map(reviewMapper::toReviewResponse).toList();
    }
}
