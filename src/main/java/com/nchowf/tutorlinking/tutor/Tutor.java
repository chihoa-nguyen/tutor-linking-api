package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tutor extends User {
    private String universityName;
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
}
