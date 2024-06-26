package com.nchowf.tutorlinking.subject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequest {
    @NotNull(message = "Tên môn học không được thiếu")
    @NotBlank(message = "Tên môn học không được trống")
    private String name;
}
