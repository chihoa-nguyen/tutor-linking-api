package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.auth.AuthRequest;
import com.nchowf.tutorlinking.auth.AuthResponse;
import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.parent.dto.ParentUpdateRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<ParentResponse> add(@RequestBody @Valid ParentRequest parentRequest) {
        return ApiResponse.<ParentResponse>builder()
                .message("Tạo mới phụ huynh thành công")
                .data(parentService.register(parentRequest))
                .build();
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthResponse> login(@RequestBody @Valid AuthRequest request) throws JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .message("Đăng nhập thành công")
                .data(parentService.authenticate(request))
                .build();
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ApiResponse<List<ParentResponse>> getAll() {
        return ApiResponse.<List<ParentResponse>>builder()
                .message("Lấy danh sách phụ huynh thành công")
                .data(parentService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ApiResponse<ParentResponse> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<ParentResponse>builder()
                .message("Lấy thông tin của phụ huynh thành công")
                .data(parentService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<ParentResponse> update(@PathVariable("id") Integer id, @RequestBody @Valid ParentUpdateRequest parentRequest) {
        return ApiResponse.<ParentResponse>builder()
                .message("Cập nhật thông tin thành công")
                .data(parentService.update(id, parentRequest))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        parentService.delete(id);
    }
}
