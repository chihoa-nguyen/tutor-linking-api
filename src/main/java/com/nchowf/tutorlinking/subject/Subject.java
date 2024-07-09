package com.nchowf.tutorlinking.subject;

import com.nchowf.tutorlinking.utils.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "subject_name_unique",
                columnNames = "name"
        )
)
public class Subject extends AbstractEntity {
    @Column(nullable = false)
    private String name;
}
