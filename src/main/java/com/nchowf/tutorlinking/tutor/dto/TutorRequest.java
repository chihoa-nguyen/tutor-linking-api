package com.nchowf.tutorlinking.tutor.dto;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorRequest extends UserRequest {
    @NotBlank(message = "Ngày sinh không được thiếu")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Ngày sinh có dạng dd/MM/yyyy")
    private String birthday;
    @NotNull(message = "Giới tính không được thiếu")
    @Min(value = 0, message = "Giới tính là 0 (Nam) hoặc 1 (Nữ)")
    @Max(value = 1, message = "Giới tính là 0 (Nam) hoặc 1 (Nữ)")
    private int gender;
    @NotNull(message = "Ảnh thẻ không được thiếu")
    private MultipartFile avt;
    @NotNull(message = "Ảnh bằng cấp được thiếu")
    private MultipartFile degree;
    @NotBlank(message = "Địa chỉ hiện tại không được thiếu")
    private String address;
    @NotBlank(message = "Tên trường đại học/cao đẳng không được thiếu")
    private String university_name;
    @NotBlank(message = "Tên chuyên ngành không được thiếu")
    private String major;
    @NotNull(message = "Chức vụ không được thiếu")
    @Min(value = 0, message = "Chức vụ là 0 (Sinh viên), 1 (Giáo viên) hoặc là 2 (Sinh viên tốt nghiệp)")
    @Max(value = 2, message = "Chức vụ là 0 (Sinh viên), 1 (Giáo viên) hoặc là 2 (Sinh viên tốt nghiệp)")
    private int position;
    @NotEmpty(message = "Số môn học tối thiểu là 1")
    private Set<String> subjects;
    @NotEmpty(message = "Số khối học tối thiểu là 1")
    private Set<String> grades;
    @NotEmpty(message = "Khu vực dạy tối thiểu là 1")
    private Set<String> teachingArea;
    @NotBlank(message = "Mô tả ưu điểm không được thiếu")
    private String desc;
}
