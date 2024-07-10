package com.nchowf.tutorlinking.grade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeRequest {
    @NotNull(message = "Tên khối không được thiếu")
    @NotBlank(message = "Tên khối không được trống")
    private String name;
}
