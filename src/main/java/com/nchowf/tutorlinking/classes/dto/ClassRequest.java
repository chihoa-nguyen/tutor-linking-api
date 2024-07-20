package com.nchowf.tutorlinking.classes.dto;

import com.nchowf.tutorlinking.utils.Address;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequest {
    @NotNull(message = "Môn học không được thiếu")
    private Set<Integer> subjects;
    @NotNull(message="Số buổi dạy không được thiếu")
    @Min(value = 1, message ="Số buổi dạy tối thiếu là 1")
    private Integer numberSession;
    @NotNull(message = "Lịch dạy không được thiếu")
    @NotBlank(message = "Lịch dạy không được để trống")
    private String time;
    @NotNull(message = "Khối dạy không được thiếu")
    private Integer gradeId;
    @Min(value = 1, message ="Học phí phải là số dương")
    private int fee;
    @NotNull(message = "Địa chỉ lớp không được thiếu")
    private Address address;
    @NotNull(message = "Yêu cầu về chức vụ của gia sư không được thiếu")
    private int positionRequired;
    @NotNull(message = "Yêu cầu về giới của gia sư không được thiếu")
    private int genderRequired;
    private String note;
}
