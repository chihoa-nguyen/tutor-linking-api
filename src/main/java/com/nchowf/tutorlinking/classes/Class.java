package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.parent.Parent;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.utils.AbstractEntity;
import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class extends AbstractEntity {
    @Column(
            nullable = false
    )
    private String address;
    @Column(nullable = false)
    private int numberSession;
    @Column(nullable = false)
    private String time;

    @ManyToMany
    @JoinTable(name = "class_subject")
//            joinColumns = @JoinColumn(
//                    name = "subject_id",
//                    referencedColumnName = "subjectId"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "class_id",
//                    referencedColumnName = "classId"
//            ))
    private Set<Subject> subjects;

    @ManyToOne(
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "grade_id",
            referencedColumnName = "id"
    )
    private Grade grade;

    private int fee;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "parent_id",
            referencedColumnName = "id"
    )
    private Parent parent;

    @Enumerated(EnumType.STRING)
    private Position positionRequired;

    @Enumerated(EnumType.STRING)
    private Gender genderRequired;
    @Lob()
    private String note;
}
