package com.nchowf.tutorlinking.class_registration;

import com.nchowf.tutorlinking.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @PostMapping("/tutor/{classId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegistrationResponse> add(@PathVariable Integer classId){
        return ApiResponse.<RegistrationResponse>builder().
                message("Đăng kí nhận lớp thành công")
                .data(registrationService.createRegistration(classId))
                .build();
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        registrationService.deleteRegistration(id);
    }
}
