package com.nchowf.tutorlinking.class_registration;

import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private Integer id;
    private ClassResponse classroom;
    private TutorResponse tutor;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
