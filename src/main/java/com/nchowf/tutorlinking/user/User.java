package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.utils.AbstractEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User extends AbstractEntity {
    private String name;
    private String email;
    private String password;
}
