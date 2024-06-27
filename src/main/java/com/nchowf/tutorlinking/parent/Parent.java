package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.utils.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = {@UniqueConstraint(
                name = "phoneNumber_unique",
                columnNames = "phoneNumber"
        ), @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )}
)
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parentId;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    private String address;
    @Column(nullable = false)
    private String password;
}
