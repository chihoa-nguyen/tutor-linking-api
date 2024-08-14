package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController{
    private final TutorService tutorService;
    @GetMapping("/verify")
    public String verify(@RequestParam String token){
        return tutorService.verifyEmail(token);
    }
    @PostMapping("/image/{id}")
    public void uploadImage(@PathVariable Integer id, @RequestPart MultipartFile avt, @RequestPart MultipartFile degree){
        tutorService.uploadAndUpdateTutorImage(id, avt, degree);
    }
    @GetMapping("")
    public ApiResponse<List<TutorResponse>> getAll() {
        return ApiResponse.<List<TutorResponse>>builder()
                .message("Lấy danh sách gia sư thành công")
                .data(tutorService.getAll())
                .build();
    }
    @GetMapping("/class/{classId}")
    public ApiResponse<List<Tutor>> getSuitableClass(@PathVariable Integer classId) {
        return ApiResponse.<List<Tutor>>builder()
                .message("Lấy danh sách gia sư phù hợp với lớp thành công")
                .data(tutorService.getTutorsSuitableForClass(classId))
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<TutorResponse> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<TutorResponse>builder()
                .message("Lấy thông tin gia sư thành công")
                .data(tutorService.getById(id))
                .build();
    }
    @GetMapping("/me")
    public ApiResponse<TutorDetailResponse> getInfor() {
        return ApiResponse.<TutorDetailResponse>builder()
                .message("Lấy thông tin gia sư thành công")
                .data(tutorService.getInforByToken())
                .build();
    }
    @PutMapping("")
    public ApiResponse<TutorDetailResponse> update(@RequestBody @Valid TutorUpdateRequest request) {
        return ApiResponse.<TutorDetailResponse>builder()
                .message("Cập nhật thông tin thành công")
                .data(tutorService.update(request))
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        tutorService.delete(id);
    }
}
