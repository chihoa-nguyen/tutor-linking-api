package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.tutor.Tutor;
import com.nchowf.tutorlinking.utils.BaseEntity;
import com.nchowf.tutorlinking.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Enrollment extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    private Tutor tutor;
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Class classroom;
    @Enumerated(EnumType.STRING)
    private Status status;
}
