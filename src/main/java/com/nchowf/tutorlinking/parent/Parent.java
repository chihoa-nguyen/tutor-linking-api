package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.user.User;
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
public class Parent extends User {
    @Column(length = 10, nullable = false)
    private String phoneNumber;
    private String address;
}
