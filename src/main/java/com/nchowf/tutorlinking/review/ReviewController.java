package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.review.dto.ReviewRequest;
import com.nchowf.tutorlinking.review.dto.ReviewResponse;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/{id}")
    public ApiResponse<ReviewResponse> create(@RequestBody @Valid ReviewRequest request, @PathVariable Integer id){
        return ApiResponse.<ReviewResponse>builder()
                .message("Đã thêm đánh giá gia sư")
                .data(reviewService.addReview(request, id))
                .build();
    }
    @GetMapping("/tutor/{tutorId}")
    public ApiResponse<List<ReviewResponse>> getReviewsOfTutor(@PathVariable Integer tutorId){
        return ApiResponse.<List<ReviewResponse>>builder()
               .message("Lấy danh sách đánh giá của gia sư thành công")
               .data(reviewService.getReviewsByTutorId(tutorId))
               .build();
    }
}
