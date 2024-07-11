package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController{
    private final TutorService tutorService;
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<TutorResponse> add(@RequestBody @Valid TutorRequest request) throws IOException, ExecutionException, InterruptedException {
        return ApiResponse.<TutorResponse>builder()
                .message("Tạo mới gia sư thành công")
                .data(tutorService.register(request))
                .build();
    }
}
