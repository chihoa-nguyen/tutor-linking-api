package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController{
    private final TutorService tutorService;
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<TutorResponse> add(@ModelAttribute @Valid TutorRequest request) throws IOException, ExecutionException, InterruptedException {
        return ApiResponse.<TutorResponse>builder()
                .message("Tạo mới gia sư thành công")
                .data(tutorService.register(request))
                .build();
    }
    @GetMapping("")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ApiResponse<List<TutorResponse>> getAll() {
        return ApiResponse.<List<TutorResponse>>builder()
                .message("Lấy danh sách gia sư thành công")
                .data(tutorService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ApiResponse<TutorResponse> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<TutorResponse>builder()
                .message("Lấy thông tin gia sư thành công")
                .data(tutorService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<TutorResponse> update(@PathVariable("id") Integer id, @RequestBody @Valid TutorUpdateRequest request) {
        return ApiResponse.<TutorResponse>builder()
                .message("Cập nhật thông tin thành công")
                .data(tutorService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        tutorService.delete(id);
    }
}
