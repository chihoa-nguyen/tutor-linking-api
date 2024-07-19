package com.nchowf.tutorlinking.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @NotNull(message = "Mã đăng ký nhận lớp không đựợc trống")
    private Integer enrollmentId;
    @Min(value = 1, message= "Số sao từ 1 tới 5")
    @Max(value = 5, message= "Số sao từ 1 tới 5")
    private int rating;
    @NotBlank(message = "Đánh giá không được trống")
    private String comment;
}
