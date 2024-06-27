package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.parent.Parent;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.utils.enums.Gender;
import com.nchowf.tutorlinking.utils.enums.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
    @Column(
            nullable = false
    )
    private String address;
    @Column(nullable = false)
    private int numberSession;
    @Column(nullable = false)
    private String time;
    @ManyToMany
    @JoinTable(name = "class_subject",
            joinColumns = @JoinColumn(
                    name ="class_id",
                    referencedColumnName = "classId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "subject_id",
                    referencedColumnName = "subjectId"
            ))
    private Set<Subject> subjects;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "grade_id",
            referencedColumnName = "gradeId"
    )
    private Grade grade;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "parent_id",
            referencedColumnName = "parentId"
    )
    private Parent parent;
    @Enumerated(EnumType.STRING)
    private Position positionRequired;
    @Enumerated(EnumType.STRING)
    private Gender genderRequired;
    @Lob
    private String note;
}
