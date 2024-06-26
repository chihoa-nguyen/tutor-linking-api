package com.nchowf.tutorlinking.grade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "grade_name_unique",
                columnNames = "name"
        )
)
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gradeId;
    @Column(nullable = false)
    private String name;
}
