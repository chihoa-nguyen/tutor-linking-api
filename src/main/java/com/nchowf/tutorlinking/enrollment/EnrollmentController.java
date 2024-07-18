package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping("/{classId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EnrollmentResponse> add(@PathVariable Integer classId){
        return ApiResponse.<EnrollmentResponse>builder().
                message("Đăng kí nhận lớp thành công")
                .data(enrollmentService.createEnrollment(classId))
                .build();
    }
    @GetMapping("/{classId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<List<EnrollmentResponse>> getEnrollmentsClass(@PathVariable Integer classId){
        return ApiResponse.<List<EnrollmentResponse>>builder()
               .message("Lấy danh sách gia sư đăng kí nhận lớp thành công")
               .data(enrollmentService.getEnrollmentsOfClass(classId))
               .build();
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<EnrollmentResponse> accept(@PathVariable("id") Integer id){
        return ApiResponse.<EnrollmentResponse>builder()
                .message("Duyệt gia sư nhận lớp thành công")
                .data(enrollmentService.acceptEnrollment(id))
                .build();
    }
    @GetMapping("/tutor")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<List<EnrollmentResponse>> getEnrollmentsTutor(){
        return ApiResponse.<List<EnrollmentResponse>>builder()
               .message("Lấy danh sách lớp đăng ký thành công")
               .data(enrollmentService.getEnrollmentsOfTutor())
               .build();
    }
    @PutMapping
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        enrollmentService.deleteEnrollment(id);
    }
}
