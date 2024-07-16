package com.nchowf.tutorlinking.grade;

import com.nchowf.tutorlinking.utils.BaseEntity;
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
public class Grade extends BaseEntity {
    @Column(nullable = false)
    private String name;
}
