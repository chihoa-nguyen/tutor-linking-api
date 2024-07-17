package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping("/tutor/{classId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EnrollmentResponse> add(@PathVariable Integer classId){
        return ApiResponse.<EnrollmentResponse>builder().
                message("Đăng kí nhận lớp thành công")
                .data(enrollmentService.createRegistration(classId))
                .build();
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        enrollmentService.deleteRegistration(id);
    }
}
