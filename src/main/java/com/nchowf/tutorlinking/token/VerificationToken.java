package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.utils.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationToken extends AbstractEntity{
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private Role role;
    public VerificationToken(Integer userId, Role role) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.role = role;
    }

}
