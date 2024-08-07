package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.parent.dto.ParentUpdateRequest;
import com.nchowf.tutorlinking.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;
    @GetMapping("/verify")
    public String verify(@RequestParam String token){
        return parentService.verifyEmail(token);
    }
    @GetMapping("")
    public ApiResponse<List<ParentResponse>> getAll() {
        return ApiResponse.<List<ParentResponse>>builder()
                .message("Lấy danh sách phụ huynh thành công")
                .data(parentService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ParentResponse> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<ParentResponse>builder()
                .message("Lấy thông tin của phụ huynh thành công")
                .data(parentService.getById(id))
                .build();
    }
    @GetMapping("/me")
    public ApiResponse<ParentResponse> getInfor() {
        return ApiResponse.<ParentResponse>builder()
                .message("Lấy thông tin của phụ huynh thành công")
                .data(parentService.getInforByToken())
                .build();
    }
    @PutMapping("")
    public ApiResponse<ParentResponse> update(@RequestBody @Valid ParentUpdateRequest parentRequest) {
        return ApiResponse.<ParentResponse>builder()
                .message("Cập nhật thông tin thành công")
                .data(parentService.update(parentRequest))
                .build();
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        parentService.delete(id);
    }
}
