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
    public ApiResponse<EnrollmentResponse> add(@PathVariable Integer classId){
        return ApiResponse.<EnrollmentResponse>builder().
                message("Đăng kí nhận lớp thành công")
                .data(enrollmentService.createEnrollment(classId))
                .build();
    }
    @GetMapping("/{classId}")
    public ApiResponse<List<EnrollmentResponse>> getEnrollmentsClass(@PathVariable Integer classId){
        return ApiResponse.<List<EnrollmentResponse>>builder()
               .message("Lấy danh sách gia sư đăng kí nhận lớp thành công")
               .data(enrollmentService.getEnrollmentsOfClass(classId))
               .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<Void> accept(@PathVariable(value = "id") Integer id){
        return ApiResponse.<Void>builder()
                .message("Duyệt gia sư nhận lớp thành công")
                .data(enrollmentService.acceptEnrollment(id))
                .build();
    }
    @PostMapping("/send-mail/{id}")
    public ApiResponse<Void> sendMail(@PathVariable Integer id){
        return ApiResponse.<Void>builder()
                .message("Gửi mail thông báo thành công")
                .data(enrollmentService.sendAcceptMail(id))
                .build();
    }
    @GetMapping("/tutor")
    public ApiResponse<List<EnrollmentResponse>> getEnrollmentsTutor(){
        return ApiResponse.<List<EnrollmentResponse>>builder()
               .message("Lấy danh sách lớp đăng ký thành công")
               .data(enrollmentService.getEnrollmentsOfTutor())
               .build();
    }
    @GetMapping("/tutor/{tutor_id}")
    public ApiResponse<List<EnrollmentResponse>> getClassTeaching(@PathVariable Integer tutor_id){
        return ApiResponse.<List<EnrollmentResponse>>builder()
                .message("Lấy danh sách lớp gia sư đang dạy thành công")
                .data(enrollmentService.getClassTeachingOfTutor(tutor_id))
                .build();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        enrollmentService.deleteEnrollment(id);
    }
}
