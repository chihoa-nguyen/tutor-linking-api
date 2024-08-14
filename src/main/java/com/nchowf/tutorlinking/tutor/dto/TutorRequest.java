package com.nchowf.tutorlinking.tutor.dto;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorRequest extends UserRequest {
    @NotBlank(message = "Ngày sinh không được thiếu")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Ngày sinh có dạng dd/MM/yyyy")
    private String birthday;
    @NotNull(message = "Giới tính không được thiếu")
    @Min(value = 0, message = "Giới tính là 0 (Nam) hoặc 1 (Nữ)")
    @Max(value = 1, message = "Giới tính là 0 (Nam) hoặc 1 (Nữ)")
    private int gender;
    @NotBlank(message = "Địa chỉ hiện tại không được thiếu")
    private String address;
    @NotBlank(message = "Tên trường đại học/cao đẳng không được thiếu")
    private String universityName;
    @NotBlank(message = "Tên chuyên ngành không được thiếu")
    private String major;
    @NotNull(message = "Chức vụ không được thiếu")
    @Min(value = 0, message = "Chức vụ là 0 (Sinh viên), 1 (Giáo viên) hoặc là 2 (Sinh viên tốt nghiệp)")
    @Max(value = 2, message = "Chức vụ là 0 (Sinh viên), 1 (Giáo viên) hoặc là 2 (Sinh viên tốt nghiệp)")
    private int position;
    @NotEmpty(message = "Số môn học tối thiểu là 1")
    private List<Integer> subjects;
    @NotEmpty(message = "Số khối học tối thiểu là 1")
    private List<Integer> grades;
    @NotEmpty(message = "Tỉnh thành không được thiếu")
    private String province;
    @NotEmpty(message = "Số khu vực dạy tối thiểu là 1")
    private List<String> teachingArea;
    @NotBlank(message = "Mô tả ưu điểm không được thiếu")
    private String description;
}
