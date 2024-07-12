package com.nchowf.tutorlinking.class_registration;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.tutor.Tutor;
import com.nchowf.tutorlinking.utils.AbstractEntity;
import com.nchowf.tutorlinking.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Registration extends AbstractEntity {
    @ManyToOne()
    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
    private Tutor tutor;
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Class classroom;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
}
