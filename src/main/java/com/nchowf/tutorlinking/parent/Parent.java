package com.nchowf.tutorlinking.parent;

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
                columnNames = "phone_number"
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
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    private String address;
    @Column(nullable = false)
    private String password;
}
