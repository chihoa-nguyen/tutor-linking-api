package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.class_registration.Registration;
import com.nchowf.tutorlinking.utils.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review extends AbstractEntity {
    @OneToOne
    @JoinColumn(name = "register_id", nullable = false)
    private Registration registration;
    private int rating;
    private String comment;
}
