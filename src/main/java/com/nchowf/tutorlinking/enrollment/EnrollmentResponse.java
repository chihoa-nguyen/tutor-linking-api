package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentResponse {
    private Integer id;
    private ClassDetailResponse classroom;
    private TutorDetailResponse tutor;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
